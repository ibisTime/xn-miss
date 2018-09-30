package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IBtcWAddressAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.BtcWAddress;
import com.ogc.standard.dto.req.XN802585Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 分页查散取地址
 * @author: taojian 
 * @since: 2018年9月11日 下午3:36:11 
 * @history:
 */
public class XN802585 extends AProcessor {

    private IBtcWAddressAO btcWAddressAO = SpringContextHolder
        .getBean(IBtcWAddressAO.class);

    private XN802585Req req;

    @Override
    public Object doBusiness() throws BizException {
        BtcWAddress condition = new BtcWAddress();
        condition.setAddressForQuery(req.getAddress());
        condition.setStatus(req.getStatus());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return btcWAddressAO.queryBtcWAddressPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802585Req.class);
        ObjValidater.validateReq(req);
    }

}
