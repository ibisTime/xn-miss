package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IUserAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dto.req.XN805170Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 微信登录
 * 1、首次登录，输入手机号，短信验证码；
 * 2、之前用户首次登录，输入手机号进行绑定
 * 3、二次登录直接登录
 * @author: xieyj 
 * @since: 2017年4月25日 下午4:40:40 
 * @history:
 */
public class XN805170 extends AProcessor {
    private IUserAO userAO = SpringContextHolder.getBean(IUserAO.class);

    private XN805170Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return userAO.doLoginWeChat(req);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805170Req.class);
        StringValidater.validateBlank(req.getCode(), req.getType());
    }

}
