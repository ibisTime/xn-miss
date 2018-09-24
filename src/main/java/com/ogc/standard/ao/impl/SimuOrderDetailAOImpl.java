package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.ISimuOrderDetailAO;
import com.ogc.standard.bo.ISimuOrderBO;
import com.ogc.standard.bo.ISimuOrderDetailBO;
import com.ogc.standard.bo.ISimuOrderHistoryBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.SimuOrder;
import com.ogc.standard.domain.SimuOrderDetail;
import com.ogc.standard.domain.SimuOrderHistory;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

@Service
public class SimuOrderDetailAOImpl implements ISimuOrderDetailAO {

    @Autowired
    private ISimuOrderDetailBO simuOrderDetailBO;

    @Autowired
    private ISimuOrderBO simuOrderBO;

    @Autowired
    private ISimuOrderHistoryBO simuOrderHistoryBO;

    @Override
    public Paginable<SimuOrderDetail> querySimuOrderDetailPage(int start,
            int limit, SimuOrderDetail condition) {
        Paginable<SimuOrderDetail> page = simuOrderDetailBO.getPaginable(start,
            limit, condition);

        if (null != page) {
            List<SimuOrderDetail> list = page.getList();
            for (SimuOrderDetail simuOrderDetail : list) {
                SimuOrderHistory simuOrderHistory = simuOrderHistoryBO
                    .getSimuOrderHistory(simuOrderDetail.getOrderCode());

                // 不存在于历史委托单，检查是否还存活
                if (null == simuOrderHistory) {

                    SimuOrder simuOrder = simuOrderBO
                        .getSimuOrder(simuOrderDetail.getOrderCode());

                    if (null == simuOrder) {

                        throw new BizException(
                            EErrorCode_main.code_NOTEXIST.getCode());

                    } else {

                        simuOrderDetail.setDirection(simuOrder.getDirection());

                    }

                } else {
                    simuOrderDetail
                        .setDirection(simuOrderHistory.getDirection());
                }

            }
        }

        return page;
    }

    @Override
    public List<SimuOrderDetail> querySimuOrderDetailList(
            SimuOrderDetail condition) {
        return simuOrderDetailBO.querySimuOrderDetailList(condition);
    }

    @Override
    public SimuOrderDetail getSimuOrderDetail(String code) {
        return simuOrderDetailBO.getSimuOrderDetail(code);
    }

}
