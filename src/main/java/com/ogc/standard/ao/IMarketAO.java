package com.ogc.standard.ao;

import java.math.BigDecimal;
import java.util.List;

import com.ogc.standard.domain.Market;
import com.ogc.standard.dto.req.XN650101Req;

/** 
 * 
 * @author: lei 
 * @since: 2018年8月22日 下午8:10:51 
 * @history:
 */
public interface IMarketAO {

    public List<Market> marketListByReq(XN650101Req req);

    public BigDecimal getMarket(String symbol, String referCurrency);

    public void saveMarket(String coin, String origin, String currency,
            String coinId, BigDecimal price);

    // 获取平台干预后的，货币价格

    public Market coinPriceByPlatform(String coin, String refCurrency);
}
