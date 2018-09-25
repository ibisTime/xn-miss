package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ISimuOrderDetailBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.ISimuOrderDetailDAO;
import com.ogc.standard.domain.SimuOrder;
import com.ogc.standard.domain.SimuOrderDetail;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.exception.BizException;

@Component
public class SimuOrderDetailBOImpl extends PaginableBOImpl<SimuOrderDetail>
        implements ISimuOrderDetailBO {

    @Autowired
    private ISimuOrderDetailDAO simuOrderDetailDAO;

    @Override
    public SimuOrderDetail saveSimuOrderDetail(SimuOrder simuOrder,
            BigDecimal tradedPrice, BigDecimal tradedCount,
            BigDecimal tradedAmount, BigDecimal tradedFee) {
        // 新增成交单并更新委托单
        SimuOrderDetail data = new SimuOrderDetail();
        if (data != null) {
            String code = OrderNoGenerater
                .generate(EGeneratePrefix.SIMU_ORDER_DETAIL.getCode());
            data.setCode(code);
            data.setOrderCode(simuOrder.getCode());
            data.setUserId(simuOrder.getUserId());
            data.setSymbol(simuOrder.getSymbol());
            data.setToSymbol(simuOrder.getToSymbol());
            data.setTradedPrice(tradedPrice);

            data.setTradedCount(tradedCount);
            data.setTradedAmount(tradedAmount);
            // 交易手续费
            data.setTradedFee(tradedFee);
            data.setCreateDatetime(new Date());
            simuOrderDetailDAO.insert(data);
        }
        return data;
    }

    @Override
    public List<SimuOrderDetail> querySimuOrderDetailList(
            SimuOrderDetail condition) {
        return simuOrderDetailDAO.selectList(condition);
    }

    @Override
    public SimuOrderDetail getSimuOrderDetail(String code) {
        SimuOrderDetail data = null;
        if (StringUtils.isNotBlank(code)) {
            SimuOrderDetail condition = new SimuOrderDetail();
            condition.setCode(code);
            data = simuOrderDetailDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
            }
        }
        return data;
    }
}
