package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IArbitrateAO;
import com.ogc.standard.ao.ITradeOrderAO;
import com.ogc.standard.bo.IArbitrateBO;
import com.ogc.standard.bo.ITencentBO;
import com.ogc.standard.bo.ITradeOrderBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Arbitrate;
import com.ogc.standard.domain.TradeOrder;
import com.ogc.standard.enums.EArbitrateStatus;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

@Service
public class ArbitrateAOImpl implements IArbitrateAO {

    @Autowired
    private IArbitrateBO arbitrateBO;

    @Autowired
    private ITradeOrderBO tradeOrderBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private ITradeOrderAO tradeOrderAO;

    @Autowired
    private ITencentBO tencentBO;

    @Override
    @Transactional
    public void handle(String code, String result, String updater,
            String remark) {

        // 工单信息
        Arbitrate arbitrate = arbitrateBO.getArbitrate(code);
        if (!EArbitrateStatus.TO_HANDLE.getCode()
            .equals(arbitrate.getStatus())) {
            throw new BizException(EErrorCode_main.arb_STAUTUS.getCode());
        }
        // 订单信息
        TradeOrder tradeOrder = tradeOrderBO
            .getTradeOrder(arbitrate.getTradeOrderCode());

        if (arbitrate.getYuangao().equals(tradeOrder.getBuyUser())) {
            // 买家申请仲裁
            doHandleBuy(tradeOrder, result, updater, remark);
        } else if (arbitrate.getYuangao().equals(tradeOrder.getSellUser())) {
            // 卖家申请仲裁
            doHandleSell(tradeOrder, result, updater, remark);
        }

        // 更新仲裁工单信息
        arbitrateBO.handle(arbitrate, result, updater, remark);

        // 发送系统消息
        tencentBO.sendNormalMessage(arbitrate.getTradeOrderCode(),
            "系统消息：仲裁申请已处理完成");
    }

    private void doHandleBuy(TradeOrder tradeOrder, String result,
            String updater, String remark) {
        if (EBoolean.YES.getCode().equals(result)) {

            // 仲裁通过，表示买家标记打款，并且实际已支付（卖家是骗子）
            tradeOrderAO.release(tradeOrder.getCode(), updater,
                "买家申请仲裁通过，买家实际已付款，自动释放", "P");

        } else {

            // 仲裁不通过，表示买家标记打款，但是实际未付款（买家是骗子）
            tradeOrderAO.platCancel(tradeOrder.getCode(), updater,
                "买家申请仲裁不通过，买家实际未付款，订单取消");

        }
    }

    private void doHandleSell(TradeOrder tradeOrder, String result,
            String updater, String remark) {

        if (EBoolean.YES.getCode().equals(result)) {

            // 仲裁通过，表示买家标记打款，但是实际未支付（买家是骗子）
            tradeOrderAO.platCancel(tradeOrder.getCode(), updater,
                "卖家申请仲裁通过，买家实际未付款，订单取消");

        } else {

            // 仲裁不通过，表示买家标记打款，并且实际已付款（卖家是骗子）
            tradeOrderAO.release(tradeOrder.getCode(), updater,
                "卖家申请仲裁不通过，买家实际已付款，自动释放", "P");
        }
    }

    @Override
    public Paginable<Arbitrate> queryArbitratePage(int start, int limit,
            Arbitrate condition) {
        Paginable<Arbitrate> results = arbitrateBO.getPaginable(start, limit,
            condition);
        for (Arbitrate arbitrate : results.getList()) {
            arbitrate.setYuangaoInfo(userBO.getUser(arbitrate.getYuangao()));
            arbitrate.setBeigaoInfo(userBO.getUser(arbitrate.getBeigao()));
            arbitrate.setTradeOrder(
                tradeOrderBO.getTradeOrder(arbitrate.getTradeOrderCode()));
        }
        return results;
    }

    @Override
    public List<Arbitrate> queryArbitrateList(Arbitrate condition) {
        return arbitrateBO.queryArbitrateList(condition);
    }

    @Override
    public Arbitrate getArbitrate(String code) {
        Arbitrate arbitrate = arbitrateBO.getArbitrate(code);
        arbitrate.setYuangaoInfo(userBO.getUser(arbitrate.getYuangao()));
        arbitrate.setBeigaoInfo(userBO.getUser(arbitrate.getBeigao()));
        arbitrate.setTradeOrder(
            tradeOrderBO.getTradeOrder(arbitrate.getTradeOrderCode()));
        return arbitrate;
    }

}
