package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IBankcardAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.domain.Bankcard;
import com.ogc.standard.dto.req.XN802026Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 列表查询银行卡
 * @author: lei 
 * @since: 2018年9月11日 下午5:38:36 
 * @history:
 */
public class XN802026 extends AProcessor {
    private IBankcardAO bankCardAO = SpringContextHolder
        .getBean(IBankcardAO.class);

    private XN802026Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Bankcard condition = new Bankcard();
        condition.setSystemCode(req.getSystemCode());
        condition.setUserId(req.getUserId());
        condition.setBankName(req.getBankName());
        condition.setBankcardNumber(req.getBankcardNumber());
        condition.setType(req.getType());
        condition.setStatus(req.getStatus());
        return bankCardAO.queryBankcardList(condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802026Req.class);
        // StringValidater.validateBlank(req.getSystemCode());
    }

}
