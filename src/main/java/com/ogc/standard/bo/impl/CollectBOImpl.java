package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ICollectBO;
import com.ogc.standard.bo.IEthTransactionBO;
import com.ogc.standard.bo.IEthWAddressBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.ICollectDAO;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.domain.Collect;
import com.ogc.standard.domain.EthWAddress;
import com.ogc.standard.domain.EthXAddress;
import com.ogc.standard.enums.ECoinType;
import com.ogc.standard.enums.ECollectStatus;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.enums.EOriginialCoin;
import com.ogc.standard.exception.BizException;

@Component
public class CollectBOImpl extends PaginableBOImpl<Collect>
        implements ICollectBO {

    private static final Logger logger = LoggerFactory
        .getLogger(CollectBOImpl.class);

    @Autowired
    private ICollectDAO collectDAO;

    @Autowired
    private IEthWAddressBO ethWAddressBO;

    //
    // @Autowired
    // private IEthXAddressBO ethXAddressBO;
    //
    @Autowired
    private IEthTransactionBO ethTransactionBO;

    @Override
    public String saveCollect(String symbol, String from, String to,
            BigDecimal value, String txHash, String refNo, String coinType) {
        String code = null;
        Collect data = new Collect();
        code = OrderNoGenerater.generate(EGeneratePrefix.Collect.getCode());
        data.setCode(code);
        data.setCurrency(symbol);
        data.setFromAddress(from);
        data.setToAddress(to);
        data.setAmount(value);
        data.setTxHash(txHash);
        data.setStatus(ECollectStatus.BROADCAST.getCode());
        data.setCreateDatetime(new Date());
        data.setRefNo(refNo);
        data.setCoinType(coinType);
        collectDAO.insert(data);
        return code;
    }

    @Override
    public List<Collect> queryCollectList(Collect condition) {
        return collectDAO.selectList(condition);
    }

    @Override
    public Collect getCollect(String code) {
        Collect data = null;
        if (StringUtils.isNotBlank(code)) {
            Collect condition = new Collect();
            condition.setCode(code);
            data = collectDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
            }
        }
        return data;
    }

    @Override
    public Collect getCollectByTxHash(String txHash) {
        Collect collect = null;
        Collect condition = new Collect();
        condition.setTxHash(txHash);
        List<Collect> results = collectDAO.selectList(condition);
        if (CollectionUtils.isNotEmpty(results)) {
            collect = results.get(0);
        }
        return collect;
    }

    @Override
    public Collect getCollectByRefNo(String refNo) {
        Collect ethCollect = null;
        Collect condition = new Collect();
        condition.setRefNo(refNo);
        List<Collect> results = collectDAO.selectList(condition);
        if (CollectionUtils.isNotEmpty(results)) {
            ethCollect = results.get(0);
        }
        return ethCollect;
    }

    @Override
    public int colectNoticeETH(Collect data, BigDecimal txfee,
            Date ethDatetime) {
        int count = 0;
        data.setTxFee(txfee);
        data.setStatus(ECollectStatus.COLLECT_YES.getCode());
        data.setConfirmDatetime(ethDatetime);
        data.setFinishDatetime(new Date());
        collectDAO.updateNoticeETH(data);
        return count;
    }

    // @Override
    // public int colectionNoticeBTC(Collect data, BigDecimal txfee,
    // Date confirmDatetime) {
    // int count = 0;
    // data.setTxFee(txfee);
    // data.setStatus(ECollectStatus.Broadcast_YES.getCode());
    // data.setConfirmDatetime(confirmDatetime);
    // data.setUpdateDatetime(new Date());
    // collectDAO.updateNoticeBTC(data);
    // return count;
    // }
    //
    // @Override
    // public int colectionNoticeSC(Collect data, String fromAddress,
    // BigDecimal txfee, Date ethDatetime) {
    // int count = 0;
    // data.setTxFee(txfee);
    // data.setFromAddress(fromAddress);
    // data.setStatus(ECollectStatus.Broadcast_YES.getCode());
    // data.setConfirmDatetime(ethDatetime);
    // data.setUpdateDatetime(new Date());
    // collectDAO.updateNoticeSC(data);
    // return count;
    // }
    //
    // @Override
    // public EthAddress getAddressUseInfo(String toAddress, String currency) {
    // Collect data = new Collect();
    // data.setToAddress(toAddress);
    // data.setCurrency(currency);
    // data.setStatus(ECollectStatus.Broadcast_YES.getCode());
    // return collectDAO.selectAddressUseInfo(data);
    // }
    //
    // @Override
    // public BigDecimal getTotalCollect(String currency) {
    // Collect condition = new Collect();
    // condition.setCurrency(currency);
    // return collectDAO.selectTotalCollect(condition);
    // }
    //

    @Override
    public void saveToCollectList(Coin coin, List<EthXAddress> fromList,
            String to, String refNo) {

        List<Collect> cList = new ArrayList<>();

        for (int i = 0; i < fromList.size(); i++) {

            EthXAddress tokenAddress = fromList.get(i);

            Collect condition = new Collect();
            condition.setFromAddress(tokenAddress.getAddress());
            condition.setStatus(ECollectStatus.TO_COLLECT.getCode());
            if (collectDAO.selectTotalCount(condition) > 0) {
                continue;
            }

            Collect data = new Collect();

            data.setCode(OrderNoGenerater.generate("GJ") + i);
            data.setCurrency(coin.getSymbol());
            data.setFromAddress(tokenAddress.getAddress());
            data.setToAddress(to);
            data.setStatus(ECollectStatus.TO_COLLECT.getCode());
            data.setCoinType(coin.getType());
            data.setRemark("等待执行归集");
            data.setRefNo(refNo);

            cList.add(data);

        }

        if (CollectionUtils.isNotEmpty(cList)) {
            collectDAO.insertList(cList);
        }

    }

    @Override
    public void doETHCollect(EthXAddress ethxAddress, String chargeCode,
            BigDecimal balance) {

        String fromAddress = ethxAddress.getAddress();

        // 获取今日归集地址
        EthWAddress wEthAddress = ethWAddressBO.getWEthAddressToday();
        String toAddress = wEthAddress.getAddress();

        // 预估矿工费用
        BigDecimal gasPrice = ethTransactionBO.getGasPrice();
        BigDecimal gasUse = new BigDecimal(21000);
        BigDecimal txFee = gasPrice.multiply(gasUse);
        BigDecimal value = balance.subtract(txFee);
        logger.info("地址余额=" + balance + "，以太坊平均价格=" + gasPrice + "，预计矿工费="
                + txFee + "，预计到账金额=" + value);
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException(EErrorCode_main.coll_LEFTCOUNT.getCode());
        }
        // 归集广播
        String txHash = ethTransactionBO.broadcast(fromAddress,
            ethxAddress.getKeystoreName(), ethxAddress.getKeystoreContent(),
            ethxAddress.getKeystorePwd(), toAddress, value);
        if (StringUtils.isBlank(txHash)) {
            throw new BizException(EErrorCode_main.coll_FAILED.getCode());
        }
        String coinType = ECoinType.ETH.getCode();
        // 归集记录落地
        saveCollect(EOriginialCoin.ETH.getCode(), fromAddress, toAddress, value,
            txHash, chargeCode, coinType);
    }

    //
    // @Override
    // public void doWanCollect(WanAddress wanAddress, String chargeCode) {
    //
    // String fromAddress = wanAddress.getAddress();
    //
    // // 获取今日归集地址
    // WanAddress wWanAddress = wanAddressBO.getWWanAddressToday();
    // String toAddress = wWanAddress.getAddress();
    //
    // // 预估矿工费用
    // BigDecimal balance = wanAddress.getBalance();
    // BigDecimal gasPrice = wanTransactionBO.getGasPrice();
    // BigDecimal gasUse = new BigDecimal(21000);
    // BigDecimal txFee = gasPrice.multiply(gasUse);
    // BigDecimal value = balance.subtract(txFee);
    // logger.info("地址余额=" + balance + "，万维链平均价格=" + gasPrice + "，预计矿工费="
    // + txFee + "，预计到账金额=" + value);
    // if (value.compareTo(BigDecimal.ZERO) <= 0) {
    // throw new BizException("xn625000", "余额不足以支付矿工费，不能归集");
    // }
    // // 归集广播
    // WanAddress secret = wanAddressBO
    // .getWanAddressSecret(wanAddress.getCode());
    // String txHash = wanTransactionBO.broadcast(fromAddress, secret,
    // toAddress, value);
    // if (StringUtils.isBlank(txHash)) {
    // throw new BizException("xn625000", "归集—交易广播失败");
    // }
    // // 归集记录落地
    // saveCollect(EOriginialCoin.WAN, fromAddress, toAddress, value, txHash,
    // chargeCode);
    // }
    //
    // @Override
    // public int colectionNoticeWAN(Collect data, BigDecimal txfee,
    // Date wanDatetime) {
    // int count = 0;
    // data.setTxFee(txfee);
    // data.setStatus(ECollectStatus.Broadcast_YES.getCode());
    // data.setConfirmDatetime(wanDatetime);
    // data.setUpdateDatetime(new Date());
    // collectDAO.updateNoticeWAN(data);
    // return count;
    // }

    @Override
    public void collectFeeBroadcastSuccess(Collect collection, String preFrom,
            BigDecimal preAmount, String preTxHash) {
        collection.setPreFrom(preFrom);
        collection.setPreTo(collection.getFromAddress());
        collection.setPreAmount(preAmount);
        collection.setPreTxHash(preTxHash);
        collection.setPreCreateDatetime(new Date());
        collection.setStatus(ECollectStatus.FEE_BROADCAST.getCode());
        collection.setRemark("归集矿工费补给广播中");
        collectDAO.collectFeeBroadcastSuccess(collection);
    }

    @Override
    public void collectBroadcastSuccess(Collect collection, BigDecimal amount,
            String txHash) {
        collection.setAmount(amount);
        collection.setTxHash(txHash);
        collection.setCreateDatetime(new Date());
        collection.setStatus(ECollectStatus.BROADCAST.getCode());
        collection.setRemark("归集广播中");
        collectDAO.collectBroadcastSuccess(collection);
    }

    @Override
    public void collectFailed(Collect collect, String remark) {
        collect.setStatus(ECollectStatus.COLLECT_NO.getCode());
        collect.setRemark(remark);
        collectDAO.collectFailed(collect);

    }

    @Override
    public void collectFeeTxSuccess(Collect collect, BigDecimal preTxFee,
            Date preConfirmDatetime) {
        collect.setPreTxFee(preTxFee);
        collect.setPreConfirmDatetime(preConfirmDatetime);
        collect.setStatus(ECollectStatus.TO_COLLECT.getCode());
        collect.setRemark("矿工费补给完成，等待归集");
        collectDAO.collectFeeTxSuccess(collect);

    }

    @Override
    public void collectTxSuccess(Collect collect, BigDecimal txFee,
            Date confirmDatetime) {
        collect.setTxFee(txFee);
        collect.setConfirmDatetime(confirmDatetime);
        collect.setStatus(ECollectStatus.COLLECT_YES.getCode());
        collect.setRemark("归集完成");
        collectDAO.collectTxSuccess(collect);

    }

}
