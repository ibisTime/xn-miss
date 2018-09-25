package com.ogc.standard.bo;

import java.math.BigDecimal;
import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.SimuOrder;
import com.ogc.standard.dto.req.XN650050Req;

public interface ISimuOrderBO extends IPaginableBO<SimuOrder> {

    public boolean isSimuOrderExist(String code);

    public SimuOrder saveSimuOrder(XN650050Req req, BigDecimal totalCount,
            BigDecimal price, BigDecimal totalAmount);

    public void tradeSuccess(SimuOrder data);

    public int cancel(SimuOrder data);

    public int refreshMarketSimuOrder(SimuOrder data);

    public int refreshLimitSimuOrder(SimuOrder data);

    public void refreshScan(String code);

    public List<SimuOrder> querySimuOrderList(SimuOrder condition);

    /**
     * 查询买方盘
     * @return 
     * @create: 2018年8月29日 下午4:58:47 lei
     * @history:
     */
    public List<SimuOrder> queryBidsHandicapList(int handicapQuantity,
            String symbol, String toSymbol);

    /**
     * 查询卖方盘
     * @return 
     * @create: 2018年8月29日 下午4:58:47 lei
     * @history:
     */
    public List<SimuOrder> queryAsksHandicapList(int handicapQuantity,
            String symbol, String toSymbol);

    public SimuOrder getSimuOrderCheck(String code);

    public SimuOrder getSimuOrder(String code);

    public List<SimuOrder> queryToScanSimuOrderList(int start, int limit);

}
