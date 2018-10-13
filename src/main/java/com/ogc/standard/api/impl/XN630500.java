package com.ogc.standard.api.impl;

import com.ogc.standard.ao.ICNavigateAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.domain.CNavigate;
import com.ogc.standard.dto.req.XN630500Req;
import com.ogc.standard.dto.res.PKCodeRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 新增导航
 * @author: zuixian 
 * @since: 2016年10月10日 下午3:58:13 
 * @history:
 */
public class XN630500 extends AProcessor {
    private ICNavigateAO cNavigateAO = SpringContextHolder
        .getBean(ICNavigateAO.class);

    private XN630500Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        CNavigate result = new CNavigate();
        result.setName(req.getName());
        result.setType(req.getType());
        result.setUrl(req.getUrl());
        result.setPic(req.getPic());
        result.setParentCode(req.getParentCode());
        result.setRemark(req.getRemark());
        String code = cNavigateAO.addCNavigate(result);
        return new PKCodeRes(code);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN630500Req.class);
        ObjValidater.validateReq(req);
    }
}
