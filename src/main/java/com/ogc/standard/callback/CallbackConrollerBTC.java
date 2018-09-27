package com.ogc.standard.callback;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ogc.standard.ao.IBtcUtxoAO;
import com.ogc.standard.bo.IBtcMAddressBO;
import com.ogc.standard.bo.IBtcXAddressBO;
import com.ogc.standard.bo.ICtqBO;
import com.ogc.standard.domain.BtcMAddress;
import com.ogc.standard.domain.BtcXAddress;
import com.ogc.standard.domain.CtqBtcUtxo;

/** 
 * @author: haiqingzheng 
 * @since: 2016年12月26日 下午1:44:16 
 * @history:
 */
@Controller
public class CallbackConrollerBTC {

    private static Logger logger = Logger.getLogger(CallbackConrollerBTC.class);

    @Autowired
    IBtcXAddressBO btcXAddressBO;

    @Autowired
    IBtcMAddressBO btcMAddressBO;

    @Autowired
    IBtcUtxoAO btcUtxoAO;

    @Autowired
    ICtqBO ctqBO;

    // BTC交易通知
    @RequestMapping("/btc/tx/notice")
    public synchronized void doBtcCallback(HttpServletRequest request,
            HttpServletResponse response) {
        List<CtqBtcUtxo> utxoList = new ArrayList<CtqBtcUtxo>();
        try {

            logger.info("*****比特币交易通知开始*****");
            logger.info(request.getParameter("btcUtxolist"));

            String txJson = request.getParameter("btcUtxolist");
            Gson gson = new Gson();
            List<CtqBtcUtxo> list = gson.fromJson(txJson,
                new TypeToken<List<CtqBtcUtxo>>() {
                }.getType());

            for (CtqBtcUtxo ctqBtcUtxo : list) {
                logger.info("处理交易：" + ctqBtcUtxo.getRefNo());
                // UTXO关联的地址
                String address = ctqBtcUtxo.getAddress();

                // 橙提取UTXO的状态
                String ctqBtcUtxoStatus = ctqBtcUtxo.getStatus();

                BtcXAddress btcXAddress = btcXAddressBO.getBtcAddress(address);
                BtcMAddress btcMAddress = btcMAddressBO.getBtcAddress(address);

                if (btcMAddress != null) {
                    btcUtxoAO.depositNotice(ctqBtcUtxo);
                    utxoList.add(ctqBtcUtxo);
                    continue;
                }

                if (btcXAddress != null) {
                    // 1、ctqBtcUtxoStatus为输出未推送 则说明是分发地址充值通知
                    String code = btcUtxoAO.chargeNotice(ctqBtcUtxo);
                    if (StringUtils.isNotBlank(code)) {
                        btcUtxoAO.collection(code);
                    }
                    utxoList.add(ctqBtcUtxo);
                    continue;
                }

                utxoList.add(ctqBtcUtxo);

            }
            logger.info("*****业务处理完成*****");
        } catch (Exception e) {
            logger.info("交易通知异常,原因：" + e.getMessage());
        } finally {
            logger.info("*****橙提取交易处理,交易个数为" + utxoList.size() + "*****");
            if (CollectionUtils.isNotEmpty(utxoList)) {
                ctqBO.confirmBTC(utxoList);
            }
            logger.info("*****complete*****");
        }
    }
}
