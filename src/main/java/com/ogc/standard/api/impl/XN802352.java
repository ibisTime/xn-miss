package com.ogc.standard.api.impl;

import org.apache.commons.collections.CollectionUtils;

import com.ogc.standard.ao.IWithdrawAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dto.req.XN802352Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 批量审批线下取现订单
 * @author: myb858 
 * @since: 2017年4月24日 下午7:51:08 
 * @history:
 */
public class XN802352 extends AProcessor {
    private IWithdrawAO withdrawAO = SpringContextHolder
        .getBean(IWithdrawAO.class);

    private XN802352Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        for (String code : req.getCodeList()) {
            withdrawAO.approveOrder(code, req.getApproveUser(),
                req.getApproveResult(), req.getApproveNote(),
                req.getSystemCode());
        }
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802352Req.class);
        if (CollectionUtils.isEmpty(req.getCodeList())) {
            throw new BizException("订单列表不能为空");
        }
        StringValidater.validateBlank(req.getApproveUser(),
            req.getApproveResult(), req.getApproveNote(), req.getSystemCode());
    }
}
