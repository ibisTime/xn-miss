package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IBtcXAddressAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.BtcXAddress;
import com.ogc.standard.dto.req.XN802565Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 分页查询BTC分发地址
 * @author: lei 
 * @since: 2018年9月25日 下午3:35:21 
 * @history:
 */
public class XN802565 extends AProcessor {

    private IBtcXAddressAO btcXAddressAO = SpringContextHolder
        .getBean(IBtcXAddressAO.class);

    private XN802565Req req;

    @Override
    public Object doBusiness() throws BizException {
        BtcXAddress condition = new BtcXAddress();
        condition.setAddressForQuery(req.getAddress());
        condition.setUserId(req.getUserId());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return btcXAddressAO.queryBtcXAddressPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802565Req.class);
        ObjValidater.validateReq(req);
    }
}
