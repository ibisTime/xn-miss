package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IHLOrderAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.HLOrder;
import com.ogc.standard.dto.req.XN803807Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 列表查询红冲蓝补订单
 * @author: xieyj 
 * @since: 2017年5月15日 下午4:07:26 
 * @history:
 */
public class XN803807 extends AProcessor {
    private IHLOrderAO hlOrderAO = SpringContextHolder
        .getBean(IHLOrderAO.class);

    private XN803807Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        HLOrder condition = new HLOrder();
        condition.setAccountNumber(req.getAccountNumber());
        condition.setAccountName(req.getAccountName());
        condition.setCurrency(req.getCurrency());
        condition.setJourCode(req.getJourCode());
        condition.setChannelType(req.getChannelType());
        condition.setDirection(req.getDirection());
        condition.setStatus(req.getStatus());

        condition.setApplyUser(req.getApplyUser());
        condition.setApplyDatetimeStart(DateUtil.getFrontDate(
            req.getApplyDateStart(), false));
        condition.setApplyDatetimeEnd(DateUtil.getFrontDate(
            req.getApplyDateEnd(), true));
        condition.setApproveUser(req.getApproveUser());
        condition.setApproveDatetimeStart(DateUtil.getFrontDate(
            req.getApproveDateStart(), false));

        condition.setApproveDatetimeEnd(DateUtil.getFrontDate(
            req.getApproveDateEnd(), true));
        condition.setSystemCode(req.getSystemCode());
        condition.setCompanyCode(req.getCompanyCode());
        return hlOrderAO.queryHLOrderList(condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN803807Req.class);
        StringValidater
            .validateBlank(req.getSystemCode(), req.getCompanyCode());
    }
}
