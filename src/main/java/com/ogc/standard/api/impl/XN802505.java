package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IEthXAddressAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.EthXAddress;
import com.ogc.standard.dto.req.XN802505Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 分页查询以太坊地址
 * @author: haiqingzheng 
 * @since: 2017年11月8日 下午3:16:17 
 * @history:
 */
public class XN802505 extends AProcessor {

    private IEthXAddressAO ethXAddressAO = SpringContextHolder
        .getBean(IEthXAddressAO.class);

    private XN802505Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        EthXAddress condition = new EthXAddress();
        condition.setAddressForQuery(req.getAddress());
        condition.setUserId(req.getUserId());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return ethXAddressAO.queryEthXAddressPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802505Req.class);
        ObjValidater.validateReq(req);
    }

}
