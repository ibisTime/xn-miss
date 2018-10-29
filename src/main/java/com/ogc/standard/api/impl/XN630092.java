package com.ogc.standard.api.impl;

import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dto.req.XN630092Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;
import com.ogc.standard.util.wechat.WechatTokenUtil;

/**
 * 获取微信JS-SDK使用权限签名等信息
 * @author: xieyj 
 * @since: 2017年3月29日 下午7:57:16 
 * @history:
 */
public class XN630092 extends AProcessor {
    private WechatTokenUtil wechatTokenUtil = SpringContextHolder
        .getBean(WechatTokenUtil.class);

    private XN630092Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return wechatTokenUtil.getSign(req.getSystemCode(),
            req.getCompanyCode(), req.getUrl());
    }

    @Override
    public void doCheck(String inputparams,String operator) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN630092Req.class);
        StringValidater.validateBlank(req.getSystemCode(),
            req.getCompanyCode(), req.getUrl());
    }
}
