package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IWithdrawAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dto.req.XN802756Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 取现详情
 * @author: xieyj 
 * @since: 2017年5月17日 下午6:35:28 
 * @history:
 */
public class XN803756 extends AProcessor {
    private IWithdrawAO withdrawAO = SpringContextHolder
        .getBean(IWithdrawAO.class);

    private XN802756Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return withdrawAO.getWithdraw(req.getCode(), req.getSystemCode());
    }

 

	@Override
	public void doCheck(String inputparams, String operator)
			throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802756Req.class);
        StringValidater.validateBlank(req.getCode(), req.getSystemCode());

    }

}
