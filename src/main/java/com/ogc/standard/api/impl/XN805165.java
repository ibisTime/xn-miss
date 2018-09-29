package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IUserIdAuthAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.UserIdAuth;
import com.ogc.standard.dto.req.XN805165Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 *分页查询认证记录
 * @author: taojian 
 * @since: 2018年9月13日 下午7:36:43 
 * @history:
 */
public class XN805165 extends AProcessor {

    private IUserIdAuthAO userIdAuthAO = SpringContextHolder
        .getBean(IUserIdAuthAO.class);

    private XN805165Req req;

    @Override
    public Object doBusiness() throws BizException {
        UserIdAuth condition = new UserIdAuth();
        condition.setApplyUser(req.getApplyUser());
        condition.setApproveUser(req.getApproveUser());
        condition.setType(req.getType());
        condition.setStatus(req.getStatus());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return userIdAuthAO.queryUserIdAuthPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805165Req.class);
        ObjValidater.validateReq(req);
    }

}
