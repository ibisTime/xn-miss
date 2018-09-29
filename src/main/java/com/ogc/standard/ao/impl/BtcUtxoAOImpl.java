/**
 * @Title BtcUtxoAOImpl.java 
 * @Package com.cdkj.coin.ao.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年11月7日 下午8:33:42 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IBtcUtxoAO;
import com.ogc.standard.ao.ICollectAO;
import com.ogc.standard.bitcoin.original.BTCOriginalTx;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IBtcMAddressBO;
import com.ogc.standard.bo.IBtcTransactionBO;
import com.ogc.standard.bo.IBtcUtxoBO;
import com.ogc.standard.bo.IBtcXAddressBO;
import com.ogc.standard.bo.IChargeBO;
import com.ogc.standard.bo.ICoinBO;
import com.ogc.standard.bo.ICollectBO;
import com.ogc.standard.bo.IDepositBO;
import com.ogc.standard.bo.IWithdrawBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.BtcMAddress;
import com.ogc.standard.domain.BtcUtxo;
import com.ogc.standard.domain.BtcXAddress;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.domain.Collect;
import com.ogc.standard.domain.CtqBtcUtxo;
import com.ogc.standard.domain.Withdraw;
import com.ogc.standard.enums.EAddressType;
import com.ogc.standard.enums.EBtcUtxoRefType;
import com.ogc.standard.enums.EBtcUtxoStatus;
import com.ogc.standard.enums.EChannelType;
import com.ogc.standard.enums.ECollectStatus;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.enums.EJourBizTypeCold;
import com.ogc.standard.enums.EJourBizTypePlat;
import com.ogc.standard.enums.EJourBizTypeUser;
import com.ogc.standard.enums.EOriginialCoin;
import com.ogc.standard.enums.ESystemAccount;
import com.ogc.standard.enums.EWithdrawStatus;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.util.BtcBlockExplorer;

/** 
 * @author: haiqingzheng 
 * @since: 2017年11月7日 下午8:33:42 
 * @history:
 */
@Service
public class BtcUtxoAOImpl implements IBtcUtxoAO {

    @Autowired
    private IChargeBO chargeBO;

    @Autowired
    private IWithdrawBO withdrawBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IBtcXAddressBO btcXAddressBO;

    @Autowired
    private IBtcUtxoBO btcUtxoBO;

    @Autowired
    private IBtcTransactionBO btcTransactionBO;

    @Autowired
    private ICoinBO coinBO;

    @Autowired
    private BtcBlockExplorer btcBlockExplorer;

    @Autowired
    private ICollectAO collectAO;

    @Autowired
    private ICollectBO collectBO;

    @Autowired
    private IBtcMAddressBO btcMAddressBO;

    @Autowired
    private IDepositBO depositBO;

    @Override
    @Transactional
    public String chargeNotice(CtqBtcUtxo ctqBtcUtxo) {

        BtcXAddress btcAddress = btcXAddressBO.getBtcAddress(ctqBtcUtxo
            .getAddress());
        if (btcAddress == null) {
            throw new BizException(
                EErrorCode_main.coin_ADDRESSNOTEXIST.getCode());
        }

        // 判断是否已经处理过该交易
        if (chargeBO.isExistOfRefNo(ctqBtcUtxo.getRefNo())) {
            return "";
        }

        Account account = accountBO.getAccountByUser(btcAddress.getUserId(),
            EOriginialCoin.BTC.getCode());
        String payGroup = OrderNoGenerater.generate(EGeneratePrefix.PAY_GROUP
            .getCode());
        BigDecimal amount = ctqBtcUtxo.getCount();

        // 哪一条链
        String cardNoInfo = EOriginialCoin.BTC.getCode();

        // 充值订单落地
        String code = chargeBO.applyOrderOnline(account, cardNoInfo, "",
            amount, EChannelType.Online, ctqBtcUtxo.getTxid(),
            EOriginialCoin.BTC.getCode() + "充值-来自交易：" + ctqBtcUtxo.getRefNo(),
            ctqBtcUtxo.getRefNo());
        // 落地UTXO
        btcUtxoBO.saveBtcUtxo(ctqBtcUtxo, EAddressType.X);

        // 落地交易记录
        BTCOriginalTx btcOriginalTx = btcBlockExplorer.queryTxHash(ctqBtcUtxo
            .getTxid());
        btcTransactionBO.saveBtcTransaction(btcOriginalTx, code);

        // 账户加钱
        accountBO.changeAmount(account, amount, EChannelType.Online, code,
            ctqBtcUtxo.getRefNo(), EJourBizTypeUser.AJ_CHARGE.getCode(),
            EChannelType.Online.getCode() + "充值-来自交易：" + ctqBtcUtxo.getRefNo());

        return code;
    }

    @Override
    @Transactional
    public void withdrawNotice(BtcUtxo btcUtxo) {

        // 判断是否是正在取现广播中的UTXO
        if (EBtcUtxoStatus.USING.getCode().equals(btcUtxo.getStatus())
                && EBtcUtxoRefType.WITHDRAW.getCode().equals(
                    btcUtxo.getRefType())
                && StringUtils.isNotBlank(btcUtxo.getRefNo())) {

            // 修改UTXO状态
            btcUtxoBO.refreshStatus(btcUtxo, EBtcUtxoStatus.USED);

            // 查询取现订单
            Withdraw withdraw = withdrawBO.getWithdraw(btcUtxo.getRefNo());
            if (withdraw == null) {
                return;
            }
            if (!EWithdrawStatus.Broadcast.getCode().equals(
                withdraw.getStatus())) {
                return;
            }

            // 查询交易详情
            BTCOriginalTx btcOriginalTx = btcBlockExplorer.queryTxHash(withdraw
                .getChannelOrder());
            if (btcOriginalTx == null) {
                return;
            }

            // 取现订单更新
            withdrawBO.payOrder(withdraw, EWithdrawStatus.Pay_YES,
                btcOriginalTx.getTxid(), "广播成功", btcOriginalTx.getTxid(),
                btcOriginalTx.getFees());

            // 落地交易记录
            btcTransactionBO.saveBtcTransaction(btcOriginalTx,
                withdraw.getCode());

            // 账户处理
            handlerAccountWithdraw(withdraw, btcOriginalTx);
        }
    }

    private void handlerAccountWithdraw(Withdraw withdraw,
            BTCOriginalTx btcOriginalTx) {
        Account userAccount = accountBO.getAccount(withdraw.getAccountNumber());
        // 取现金额解冻
        userAccount = accountBO.unfrozenAmount(userAccount,
            withdraw.getAmount(),
            EJourBizTypeUser.AJ_WITHDRAW_UNFROZEN.getCode(),
            EJourBizTypeUser.AJ_WITHDRAW_UNFROZEN.getValue(),
            withdraw.getCode());
        // 取现金额扣减
        userAccount = accountBO.changeAmount(
            userAccount,
            withdraw.getAmount().subtract(withdraw.getFee()).negate(),
            EChannelType.Online,
            withdraw.getChannelOrder(),
            withdraw.getCode(),
            EJourBizTypeUser.AJ_WITHDRAW.getCode(),
            EJourBizTypeUser.AJ_WITHDRAW.getValue() + "-外部地址："
                    + withdraw.getPayCardNo());
        if (withdraw.getFee().compareTo(BigDecimal.ZERO) > 0) {
            // 取现手续费扣减
            userAccount = accountBO.changeAmount(userAccount, withdraw.getFee()
                .negate(), EChannelType.Online, withdraw.getChannelOrder(),
                withdraw.getCode(), EJourBizTypeUser.AJ_WITHDRAW_FEE.getCode(),
                EJourBizTypeUser.AJ_WITHDRAW_FEE.getValue());
        }

        // 平台盈亏账户记入取现手续费
        Account sysAccount = accountBO.getAccount(ESystemAccount.SYS_ACOUNT_BTC
            .getCode());
        if (withdraw.getFee().compareTo(BigDecimal.ZERO) > 0) {
            sysAccount = accountBO.changeAmount(sysAccount, withdraw.getFee(),
                EChannelType.Online, withdraw.getChannelOrder(),
                withdraw.getCode(), EJourBizTypePlat.AJ_WITHDRAW_FEE.getCode(),
                EJourBizTypePlat.AJ_WITHDRAW_FEE.getValue() + "-外部地址："
                        + withdraw.getPayCardNo());
        }
        // 平台盈亏账户记入取现矿工费
        sysAccount = accountBO.changeAmount(sysAccount, btcOriginalTx.getFees()
            .negate(), EChannelType.Online, withdraw.getChannelOrder(),
            withdraw.getCode(), EJourBizTypePlat.AJ_WITHDRAW_MINING_FEE
                .getCode(), EJourBizTypePlat.AJ_WITHDRAW_MINING_FEE.getValue()
                    + "-外部地址：" + withdraw.getPayCardNo());
    }

    @Override
    @Transactional
    public void collection(String chargeCode) {

        // 归集阀值，UTXO大于这个值进行归集
        Coin coin = coinBO.getCoin(EOriginialCoin.BTC.getCode());
        BigDecimal balanceStart = coin.getCollectStart();
        balanceStart = balanceStart.setScale(0, RoundingMode.DOWN);

        // 开始归集
        collectAO.collect(balanceStart, EOriginialCoin.BTC.getCode(),
            chargeCode);

    }

    @Override
    @Transactional
    public void collectionNotice(BtcUtxo btcUtxo) {

        // 判断是否是正在归集广播中的UTXO
        if (EBtcUtxoStatus.USING.getCode().equals(btcUtxo.getStatus())
                && EBtcUtxoRefType.COLLECTION.getCode().equals(
                    btcUtxo.getRefType())
                && StringUtils.isNotBlank(btcUtxo.getRefNo())) {

            // 修改UTXO状态
            btcUtxoBO.refreshStatus(btcUtxo, EBtcUtxoStatus.USED);

            // 根据交易hash查询归集记录
            Collect collect = collectBO.getCollect(btcUtxo.getRefNo());

            if (!ECollectStatus.BROADCAST.getCode().equals(collect.getStatus())) {
                return;
            }

            // 查询交易详情
            BTCOriginalTx btcOriginalTx = btcBlockExplorer.queryTxHash(collect
                .getTxHash());
            if (btcOriginalTx == null) {
                return;
            }

            // 归集订单状态更新
            collectBO.colectNoticeBTC(collect, btcOriginalTx.getFees(),
                DateUtil.TimeStamp2Date(
                    btcOriginalTx.getBlocktime().toString(),
                    DateUtil.DATA_TIME_PATTERN_1));

            // 落地交易记录
            btcTransactionBO.saveBtcTransaction(btcOriginalTx,
                collect.getCode());

            // 平台冷钱包加钱
            Account coldAccount = accountBO
                .getAccount(ESystemAccount.SYS_ACOUNT_BTC_COLD.getCode());
            BigDecimal amount = collect.getAmount();
            accountBO.changeAmount(coldAccount, amount, EChannelType.Online,
                btcOriginalTx.getTxid(), collect.getCode(),
                EJourBizTypeCold.AJ_COLLECT.getCode(), "归集-交易ID："
                        + btcOriginalTx.getTxid());

            // 平台盈亏账户记入矿工费
            Account sysAccount = accountBO
                .getAccount(ESystemAccount.SYS_ACOUNT_BTC.getCode());
            accountBO.changeAmount(sysAccount,
                btcOriginalTx.getFees().negate(), EChannelType.Online,
                btcOriginalTx.getTxid(), collect.getCode(),
                EJourBizTypePlat.AJ_COLLECT_FIRST_MINING_FEE.getCode(),
                "归集-交易ID：" + btcOriginalTx.getTxid());

        }

    }

    @Override
    public Paginable<BtcUtxo> queryBtcUtxoPage(int start, int limit,
            BtcUtxo condition) {
        return btcUtxoBO.getPaginable(start, limit, condition);
    }

    @Override
    @Transactional
    public void depositNotice(CtqBtcUtxo ctqBtcUtxo) {
        BtcMAddress btcAddress = btcMAddressBO.getBtcAddress(ctqBtcUtxo
            .getAddress());
        if (btcAddress == null) {
            throw new BizException(
                EErrorCode_main.coin_ADDRESSNOTEXIST.getCode());
        }

        // 平台冷钱包减钱
        BigDecimal amount = ctqBtcUtxo.getCount();
        Account coldAccount = accountBO
            .getAccount(ESystemAccount.SYS_ACOUNT_BTC_COLD.getCode());
        coldAccount = accountBO.changeAmount(
            coldAccount,
            amount.negate(),
            EChannelType.Online,
            ctqBtcUtxo.getRefNo(),
            ctqBtcUtxo.getRefNo(),
            EJourBizTypeCold.AJ_DEPOSIT.getCode(),
            EChannelType.Online.getCode() + "定存至取现地址(M):"
                    + ctqBtcUtxo.getAddress());
        // 查询交易详情
        BTCOriginalTx btcOriginalTx = btcBlockExplorer.queryTxHash(ctqBtcUtxo
            .getTxid());
        String symbol = EOriginialCoin.BTC.getCode();
        // from为空
        // 落地定存记录
        String code = depositBO.saveDeposit(symbol, "",
            ctqBtcUtxo.getAddress(), ctqBtcUtxo.getCount(),
            ctqBtcUtxo.getTxid(), btcOriginalTx.getFees(),
            ctqBtcUtxo.getSyncTime());
        // 落地UTXO
        btcUtxoBO.saveBtcUtxo(ctqBtcUtxo, EAddressType.M);
        // 落地交易记录
        btcTransactionBO.saveBtcTransaction(btcOriginalTx, code);
        // 平台盈亏账户记入定存矿工费
        Account sysAccount = accountBO.getAccount(ESystemAccount.SYS_ACOUNT_BTC
            .getCode());
        accountBO.changeAmount(sysAccount, btcOriginalTx.getFees().negate(),
            EChannelType.Online, btcOriginalTx.getTxid(), code,
            EJourBizTypePlat.AJ_DEPOSIT_MINING_FEE.getCode(), "-交易ID："
                    + btcOriginalTx.getTxid());
    }

    @Override
    public BigDecimal getTotalEnableUTXOCount(EAddressType addressType) {
        return btcUtxoBO.getTotalEnableUTXOCount(addressType);
    }

}
