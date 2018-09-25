/**
 * @Title XN805044.java 
 * @Package com.ogc.standard.api.impl 
 * @Description 
 * @author taojian  
 * @date 2018年9月14日 下午3:35:32 
 * @version V1.0   
 */
package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IUserAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN805044Req;
import com.ogc.standard.dto.res.PKCodeRes;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 代注册渠道商
 * @author: taojian 
 * @since: 2018年9月14日 下午3:35:32 
 * @history:
 */
public class XN805044 extends AProcessor {

    private IUserAO userAO = SpringContextHolder.getBean(IUserAO.class);

    private XN805044Req req;

    @Override
    public Object doBusiness() throws BizException {
        String userId = userAO.doAddQDS(req.getMobile(), req.getIdKind(),
            req.getIdNo(), req.getRealName(), req.getRespArea(),
            req.getLanguage());

        return new PKCodeRes(userId);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805044Req.class);
        ObjValidater.validateReq(req);
    }

}
