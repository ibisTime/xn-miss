package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IUserFieldApproveAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.UserFieldApprove;
import com.ogc.standard.dto.req.XN805135Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 分页查询审核信息
 * @author: taojian 
 * @since: 2018年9月13日 下午2:54:34 
 * @history:
 */
public class XN805135 extends AProcessor {

    private IUserFieldApproveAO userFieldApproveAO = SpringContextHolder
        .getBean(IUserFieldApproveAO.class);

    private XN805135Req req;

    @Override
    public Object doBusiness() throws BizException {
        UserFieldApprove condition = new UserFieldApprove();
        condition.setApplyUser(req.getApplyUser());
        condition.setApproveUser(req.getApproveUser());
        condition.setType(req.getType());
        condition.setStatus(req.getStatus());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return userFieldApproveAO.queryUserFieldApprovePage(start, limit,
            condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805135Req.class);
        ObjValidater.validateReq(req);
    }

}
