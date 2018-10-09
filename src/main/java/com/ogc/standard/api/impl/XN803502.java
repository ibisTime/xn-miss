package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IAccountAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dto.req.XN802502Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 账户详情
 * @author: xieyj 
 * @since: 2016年12月23日 下午8:36:10 
 * @history:
 */
public class XN803502 extends AProcessor {

    private IAccountAO accountAO = SpringContextHolder
        .getBean(IAccountAO.class);

    private XN802502Req req = null;

    /** 
    * @see com.xnjr.base.api.IProcessor#doBusiness()
    */
    @Override
    public Object doBusiness() throws BizException {
        return accountAO.getAccount(req.getAccountNumber());
    }

    /** 
    * @see com.xnjr.base.api.IProcessor#doCheck(java.lang.String)
    */
	@Override
	public void doCheck(String inputparams, String operator)
			throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802502Req.class);
        StringValidater.validateBlank(req.getAccountNumber());
    }
}
