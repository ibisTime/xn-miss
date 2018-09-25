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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IBtcUtxoAO;
import com.ogc.standard.bitcoin.original.BTCOriginalTx;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IBtcTransactionBO;
import com.ogc.standard.bo.IBtcUtxoBO;
import com.ogc.standard.bo.IBtcXAddressBO;
import com.ogc.standard.bo.IChargeBO;
import com.ogc.standard.bo.ICoinBO;
import com.ogc.standard.bo.IWithdrawBO;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.BtcUtxo;
import com.ogc.standard.domain.BtcXAddress;
import com.ogc.standard.domain.CtqBtcUtxo;
import com.ogc.standard.domain.Withdraw;
import com.ogc.standard.enums.EAddressType;
import com.ogc.standard.enums.EBtcUtxoRefType;
import com.ogc.standard.enums.EBtcUtxoStatus;
import com.ogc.standard.enums.EChannelType;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.enums.EJourBizTypeUser;
import com.ogc.standard.enums.EOriginialCoin;
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

        // 充值订单落地
        // String code = chargeBO.applyOrderOnline(account,
        // ctqBtcUtxo.getRefNo(), EJourBizTypeUser.AJ_CHARGE.getCode(),
        // EOriginialCoin.BTC.getCode() + "充值-来自交易：" + ctqBtcUtxo.getRefNo(),
        // amount, EChannelType.Online, account.getUserId(),
        // ctqBtcUtxo.getRefNo());
        String code = "";
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
    public void withdrawNotice(CtqBtcUtxo ctqBtcUtxo) {

        // 取到UTXO
        BtcUtxo btcUtxo = btcUtxoBO.getBtcUtxo(ctqBtcUtxo.getTxid(),
            ctqBtcUtxo.getVout());

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
        // // 取现金额扣减
        // userAccount = accountBO.changeAmount(userAccount,
        // withdraw.getAmount().subtract(withdraw.getFee()).negate(),
        // EChannelType.Online, withdraw.getChannelOrder(), "BTC",
        // withdraw.getCode(), EJourBizTypeUser.AJ_WITHDRAW.getCode(),
        // EJourBizTypeUser.AJ_WITHDRAW.getValue() + "-外部地址："
        // + withdraw.getPayCardNo());
        // if (withdraw.getFee().compareTo(BigDecimal.ZERO) > 0) {
        // // 取现手续费扣减
        // userAccount = accountBO.changeAmount(userAccount,
        // withdraw.getFee().negate(), EChannelType.Online,
        // withdraw.getChannelOrder(), "BTC", withdraw.getCode(),
        // EJourBizTypeUser.AJ_WITHDRAWFEE.getCode(),
        // EJourBizTypeUser.AJ_WITHDRAWFEE.getValue());
    }

    // // 平台盈亏账户记入取现手续费
    // Account sysAccount = accountBO
    // .getAccount(ESystemAccount.SYS_ACOUNT_BTC.getCode());
    // if (withdraw.getFee().compareTo(BigDecimal.ZERO) > 0) {
    // sysAccount = accountBO.changeAmount(sysAccount, withdraw.getFee(),
    // EChannelType.Online, withdraw.getChannelOrder(),
    // EChannelType.Online.getCode(), withdraw.getCode(),
    // EJourBizTypePlat.AJ_WITHDRAWFEE.getCode(),
    // EJourBizTypePlat.AJ_WITHDRAWFEE.getValue() + "-外部地址："
    // + withdraw.getPayCardNo());
    // }
    // // 平台盈亏账户记入取现矿工费
    // sysAccount = accountBO.changeAmount(sysAccount,
    // btcOriginalTx.getFees().negate(), EChannelType.Online,
    // withdraw.getChannelOrder(), EChannelType.Online.getCode(),
    // withdraw.getCode(), EJourBizTypePlat.AJ_WFEE.getCode(),
    // EJourBizTypePlat.AJ_WFEE.getValue() + "-外部地址："
    // + withdraw.getPayCardNo());
    // }
    //
    // @Override
    // @Transactional
    // public void collection(String chargeCode) {
    //
    // // 归集阀值，UTXO大于这个值进行归集
    // Coin coin = coinBO.getCoin(EOriginialCoin.BTC.getCode());
    // BigDecimal balanceStart = coin.getCollectStart();
    // balanceStart = balanceStart.setScale(0, RoundingMode.DOWN);
    //
    // // 开始归集
    // collectionAO.collect(balanceStart, EOriginialCoin.BTC.getCode(),
    // chargeCode);
    //
    // }
    //
    // @Override
    // @Transactional
    // public void collectionNotice(CtqBtcUtxo ctqBtcUtxo) {
    //
    // // 取到UTXO
    // BtcUtxo btcUtxo = btcUtxoBO.getBtcUtxo(ctqBtcUtxo.getTxid(),
    // ctqBtcUtxo.getVout());
    //
    // // 判断是否是正在归集广播中的UTXO
    // if (EBtcUtxoStatus.USING.getCode().equals(btcUtxo.getStatus())
    // && EBtcUtxoRefType.COLLECTION.getCode()
    // .equals(btcUtxo.getRefType())
    // && StringUtils.isNotBlank(btcUtxo.getRefNo())) {
    //
    // // 修改UTXO状态
    // btcUtxoBO.refreshStatus(btcUtxo, EBtcUtxoStatus.USED);
    //
    // // 根据交易hash查询归集记录
    // Collection collection = collectionBO
    // .getCollection(btcUtxo.getRefNo());
    //
    // if (!ECollectionStatus.Broadcast.getCode()
    // .equals(collection.getStatus())) {
    // return;
    // }
    //
    // // 查询交易详情
    // BTCOriginalTx btcOriginalTx = btcBlockExplorer
    // .queryTxHash(collection.getTxHash());
    // if (btcOriginalTx == null) {
    // return;
    // }
    //
    // // 归集订单状态更新
    // collectionBO.colectionNoticeBTC(collection, btcOriginalTx.getFees(),
    // DateUtil.TimeStamp2Date(btcOriginalTx.getBlocktime().toString(),
    // DateUtil.DATA_TIME_PATTERN_1));
    //
    // // 落地交易记录
    // btcTransactionBO.saveBtcTransaction(btcOriginalTx,
    // collection.getCode());
    //
    // // 平台冷钱包加钱
    // Account coldAccount = accountBO
    // .getAccount(ESystemAccount.SYS_ACOUNT_BTC_COLD.getCode());
    // BigDecimal amount = collection.getAmount();
    // accountBO.changeAmount(coldAccount, amount, EChannelType.Online,
    // btcOriginalTx.getTxid(), EChannelType.Online.getCode(),
    // collection.getCode(), EJourBizTypeCold.AJ_INCOME.getCode(),
    // "归集-交易ID：" + btcOriginalTx.getTxid());
    //
    // // 平台盈亏账户记入矿工费
    // Account sysAccount = accountBO
    // .getAccount(ESystemAccount.SYS_ACOUNT_BTC.getCode());
    // accountBO.changeAmount(sysAccount, btcOriginalTx.getFees().negate(),
    // EChannelType.Online, btcOriginalTx.getTxid(),
    // EChannelType.Online.getCode(), collection.getCode(),
    // EJourBizTypePlat.AJ_MFEE.getCode(),
    // "归集-交易ID：" + btcOriginalTx.getTxid());
    //
    // }
    //
    // }
    //
    // @Override
    // public Paginable<BtcUtxo> queryBtcUtxoPage(int start, int limit,
    // BtcUtxo condition) {
    // return btcUtxoBO.getPaginable(start, limit, condition);
    // }
    //
    // @Override
    // @Transactional
    // public void depositNotice(CtqBtcUtxo ctqBtcUtxo) {
    // // 平台冷钱包减钱
    // BigDecimal amount = ctqBtcUtxo.getCount();
    // Account coldAccount = accountBO
    // .getAccount(ESystemAccount.SYS_ACOUNT_BTC_COLD.getCode());
    // coldAccount = accountBO.changeAmount(coldAccount, amount.negate(),
    // EChannelType.Online, ctqBtcUtxo.getRefNo(),
    // EChannelType.Online.getCode(), ctqBtcUtxo.getRefNo(),
    // EJourBizTypeCold.AJ_PAY.getCode(), EChannelType.Online.getCode()
    // + "定存至取现地址(M):" + ctqBtcUtxo.getAddress());
    // // 落地UTXO
    // btcUtxoBO.saveBtcUtxo(ctqBtcUtxo, EAddressType.M);
    // }
    //
    // @Override
    // public BigDecimal getTotalEnableUTXOCount(EAddressType addressType) {
    // return btcUtxoBO.getTotalEnableUTXOCount(addressType);
    // }

}
