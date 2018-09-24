package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ISimuOrderBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.ISimuOrderDAO;
import com.ogc.standard.domain.SimuOrder;
import com.ogc.standard.dto.req.XN650050Req;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.enums.ESimuOrderDirection;
import com.ogc.standard.enums.ESimuOrderStatus;
import com.ogc.standard.enums.ESimuOrderType;
import com.ogc.standard.exception.BizException;

@Component
public class SimuOrderBOImpl extends PaginableBOImpl<SimuOrder>
        implements ISimuOrderBO {

    @Autowired
    private ISimuOrderDAO simuOrderDAO;

    @Override
    public boolean isSimuOrderExist(String code) {
        SimuOrder condition = new SimuOrder();
        condition.setCode(code);
        if (simuOrderDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public SimuOrder saveSimuOrder(XN650050Req req, BigDecimal totalCount,
            BigDecimal price, BigDecimal totalAmount) {
        String code = OrderNoGenerater
            .generate(EGeneratePrefix.SIMU_ORDER.getCode());
        SimuOrder simuOrder = new SimuOrder();
        simuOrder.setCode(code);
        simuOrder.setUserId(req.getUserId());
        simuOrder.setSymbol(req.getSymbol());
        simuOrder.setToSymbol(req.getToSymbol());
        simuOrder.setType(req.getType());

        simuOrder.setDirection(req.getDirection());
        simuOrder.setPrice(price);
        simuOrder.setTotalCount(totalCount);
        simuOrder.setTotalAmount(totalAmount);
        simuOrder.setTradedCount(BigDecimal.ZERO);

        simuOrder.setTradedAmount(BigDecimal.ZERO);
        simuOrder.setTradedFee(BigDecimal.ZERO);
        simuOrder.setLastTradedDatetime(new Date());
        simuOrder.setCreateDatetime(new Date());
        simuOrder.setStatus(ESimuOrderStatus.SUBMIT.getCode());

        simuOrderDAO.insert(simuOrder);

        return simuOrder;
    }

    @Override
    public void tradeSuccess(SimuOrder data) {
        if (data != null) {
            simuOrderDAO.tradeSuccess(data);
        }
    }

    @Override
    public int refreshMarketSimuOrder(SimuOrder data) {
        int count = 0;
        if (data != null && StringUtils.isNotBlank(data.getCode())) {
            count = simuOrderDAO.updateMarketSimuOrder(data);
        }
        return count;
    }

    @Override
    public int refreshLimitSimuOrder(SimuOrder data) {
        int count = 0;
        if (data != null && StringUtils.isNotBlank(data.getCode())) {
            count = simuOrderDAO.updateLimitSimuOrder(data);
        }
        return count;
    }

    @Override
    public int cancel(SimuOrder data) {
        int count = 0;
        if (data != null && StringUtils.isNotBlank(data.getCode())) {
            count = simuOrderDAO.cancel(data);
        }
        return count;
    }

    @Override
    public List<SimuOrder> querySimuOrderList(SimuOrder condition) {
        return simuOrderDAO.selectList(condition);
    }

    @Override
    public List<SimuOrder> queryBidsHandicapList(int handicapQuantity,
            String symbol, String toSymbol) {
        SimuOrder condition = new SimuOrder();
        condition.setSymbol(symbol);
        condition.setToSymbol(toSymbol);
        condition.setType(ESimuOrderType.LIMIT.getCode());
        condition.setDirection(ESimuOrderDirection.BUY.getCode());

        condition.setOrder("price desc, create_datetime desc");

        Paginable<SimuOrder> pages = getPaginable(1, handicapQuantity,
            condition);

        return pages.getList();
    }

    @Override
    public List<SimuOrder> queryAsksHandicapList(int handicapQuantity,
            String symbol, String toSymbol) {
        SimuOrder condition = new SimuOrder();
        condition.setSymbol(symbol);
        condition.setToSymbol(toSymbol);
        condition.setType(ESimuOrderType.LIMIT.getCode());
        condition.setDirection(ESimuOrderDirection.SELL.getCode());

        condition.setOrder("price asc, create_datetime asc");

        Paginable<SimuOrder> pages = getPaginable(1, handicapQuantity,
            condition);

        return pages.getList();
    }

    @Override
    public SimuOrder getSimuOrderCheck(String code) {
        SimuOrder data = null;
        if (StringUtils.isNotBlank(code)) {
            SimuOrder condition = new SimuOrder();
            condition.setCode(code);
            data = simuOrderDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
            }
        }
        return data;
    }

    @Override
    public SimuOrder getSimuOrder(String code) {
        SimuOrder data = null;
        if (StringUtils.isNotBlank(code)) {
            SimuOrder condition = new SimuOrder();
            condition.setCode(code);
            data = simuOrderDAO.select(condition);
        }
        return data;
    }

}
