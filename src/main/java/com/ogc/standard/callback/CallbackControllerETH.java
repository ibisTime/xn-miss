package com.ogc.standard.callback;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ogc.standard.ao.IEthTransactionAO;
import com.ogc.standard.bo.ICtqBO;
import com.ogc.standard.bo.IEthMAddressBO;
import com.ogc.standard.bo.IEthSAddressBO;
import com.ogc.standard.bo.IEthXAddressBO;
import com.ogc.standard.domain.CtqEthTransaction;
import com.ogc.standard.domain.EthMAddress;
import com.ogc.standard.domain.EthSAddress;
import com.ogc.standard.domain.EthXAddress;
import com.ogc.standard.domain.TokenEvent;
import com.ogc.standard.util.ListUtil;

/** 
 * @author: haiqingzheng 
 * @since: 2016年12月26日 下午1:44:16 
 * @history:
 */
@Controller
public class CallbackControllerETH {

    private static Logger logger = Logger
        .getLogger(CallbackControllerETH.class);

    @Autowired
    private IEthXAddressBO ethXAddressBO;

    @Autowired
    private IEthMAddressBO ethMAddressBO;

    @Autowired
    private IEthTransactionAO ethTransactionAO;

    @Autowired
    ICtqBO ctqBO;

    @Autowired
    private IEthSAddressBO ethSAddressBO;

    // ETH和token交易通知，只有充值订单
    @RequestMapping("/eth/tx/notice")
    public synchronized void doEthCallback(HttpServletRequest request,
            HttpServletResponse response) {
        List<String> hashList = new ArrayList<String>();
        try {
            logger.info("*****以太坊交易通知开始*****");
            logger.info(request.getParameter("ethTxlist"));
            String txJson = request.getParameter("ethTxlist");
            Gson gson = new Gson();
            List<CtqEthTransaction> list = gson.fromJson(txJson,
                new TypeToken<List<CtqEthTransaction>>() {
                }.getType());

            for (CtqEthTransaction ctqEthTransaction : list) {
                EthXAddress xToAddress = null;
                EthMAddress mToAddress = null;
                // tokenEventList是空的，表示eth交易，不是空的表示token交易
                if (CollectionUtils.isNotEmpty(ctqEthTransaction
                    .getTokenEventList())) {
                    // token交易
                    for (TokenEvent tokenEvent : ctqEthTransaction
                        .getTokenEventList()) {
                        // 查询地址是否是X充值地址
                        xToAddress = ethXAddressBO
                            .getEthXAddressByAddress(tokenEvent.getTokenTo());
                        if (null != xToAddress) {// toAddress=X 充值
                            ethTransactionAO.tokenChargeNotice(
                                ctqEthTransaction, tokenEvent);
                            hashList.add(ctqEthTransaction.getHash());
                            continue;
                        }
                        // 查询地址是否是M定存充值地址
                        mToAddress = ethMAddressBO
                            .getEthMAddressByAddress(tokenEvent.getTokenTo());
                        if (null != mToAddress) {// 散取地址每日定存
                            ethTransactionAO.tokenDepositNotice(
                                ctqEthTransaction, tokenEvent);
                            hashList.add(ctqEthTransaction.getHash());
                        }
                    }
                } else {
                    // eth交易
                    // 查询地址是否是X充值地址
                    xToAddress = ethXAddressBO
                        .getEthXAddressByAddress(ctqEthTransaction.getTo());
                    if (null != xToAddress) {// toAddress=X 充值
                        ethTransactionAO.ethChargeNotice(ctqEthTransaction);
                        hashList.add(ctqEthTransaction.getHash());
                        continue;
                    }
                    // 查询地址是否是M定存充值地址
                    mToAddress = ethMAddressBO
                        .getEthMAddressByAddress(ctqEthTransaction.getTo());
                    if (null != mToAddress) {// 散取地址每日定存
                        ethTransactionAO.ethDepositNotice(ctqEthTransaction);
                        hashList.add(ctqEthTransaction.getHash());
                        continue;
                    }
                    // 查询s是否是补给地址
                    EthSAddress ethSAddress = ethSAddressBO
                        .getEthSAddressByAddress(ctqEthTransaction.getTo());
                    if (null != ethSAddress) {// 矿工费补给地址每日定存
                        ethTransactionAO
                            .ethSupplyDepositNotice(ctqEthTransaction);
                    }
                }

                if (null == xToAddress && null == mToAddress) {// 外部交易直接落地，不进行充值处理
                    ethTransactionAO.addTransaction(ctqEthTransaction);
                    hashList.add(ctqEthTransaction.getHash());
                }
                logger.info("处理交易：" + ctqEthTransaction.getHash());
            }
            logger.info("*****业务处理完成*****");
        } catch (Exception e) {
            logger.info("交易通知异常,原因：" + e.getMessage());
        } finally {
            logger.info("*****橙提取交易确认,交易个数为" + hashList.size() + "*****");
            if (CollectionUtils.isNotEmpty(hashList)) {
                hashList = ListUtil.removeDuplicate(hashList);
                ctqBO.confirmEth(hashList);
            }
            logger.info("*****complete*****");
        }
    }
}
