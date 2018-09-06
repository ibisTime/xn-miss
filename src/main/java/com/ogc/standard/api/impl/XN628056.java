package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IPostAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN628056Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 详情查询帖子(oss)
 * @author: silver 
 * @since: 2018年8月22日 下午4:17:49 
 * @history:
 */
public class XN628056 extends AProcessor {

    private IPostAO postAO = SpringContextHolder.getBean(IPostAO.class);

    private XN628056Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return postAO.getOssPost(req.getCode());
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN628056Req.class);
        ObjValidater.validateReq(req);
    }
}