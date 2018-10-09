package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IAccountAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dto.req.XN802503Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 根据用户编号，币种获取账户列表
 * @author: xieyj 
 * @since: 2016年12月24日 下午1:05:33 
 * @history:
 */
public class XN803503 extends AProcessor {

    private IAccountAO accountAO = SpringContextHolder
        .getBean(IAccountAO.class);

    private XN802503Req req = null;

    /** 
    * @see com.xnjr.base.api.IProcessor#doBusiness()
    */
    @Override
    public Object doBusiness() throws BizException {
        return accountAO.getAccountByUserId(req.getUserId(), req.getCurrency());
    }

    /** 
    * @see com.xnjr.base.api.IProcessor#doCheck(java.lang.String)
    */
	@Override
	public void doCheck(String inputparams, String operator)
			throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802503Req.class);
        StringValidater.validateBlank(req.getUserId());
    }
}
