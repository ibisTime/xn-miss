package com.ogc.standard.api.impl;

import org.apache.commons.collections.CollectionUtils;

import com.ogc.standard.ao.IEthMAddressAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.EthMAddress;
import com.ogc.standard.dto.req.XN802515Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 分页查询以太坊地址
 * @author: haiqingzheng 
 * @since: 2017年11月8日 下午3:16:17 
 * @history:
 */
public class XN802515 extends AProcessor {

    private IEthMAddressAO ethMAddressAO = SpringContextHolder
        .getBean(IEthMAddressAO.class);

    private XN802515Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        EthMAddress condition = new EthMAddress();
        condition.setAddressForQuery(req.getAddress());
        condition.setStatus(req.getStatus());
        if (CollectionUtils.isNotEmpty(req.getStatusList())) {
            condition.setStatusList(req.getStatusList());
        }
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return ethMAddressAO.queryEthMAddressPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802515Req.class);
        ObjValidater.validateReq(req);
    }

}
