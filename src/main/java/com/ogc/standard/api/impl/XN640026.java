package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IRankAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN640026Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 详情查询榜单
 * @author: jiafr 
 * @since: 2018年10月12日 上午11:28:05 
 * @history:
 */
public class XN640026 extends AProcessor {
    private IRankAO rankAO = SpringContextHolder.getBean(IRankAO.class);

    private XN640026Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return rankAO.getRank(req.getCode());
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640026Req.class);
        ObjValidater.validateReq(req);
    }
}
