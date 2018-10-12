/**
 * @Title XN640005.java 
 * @Package com.ogc.standard.api.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月12日 上午11:36:59 
 * @version V1.0   
 */
package com.ogc.standard.api.impl;

import com.ogc.standard.ao.ICommentAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.dto.req.XN640005Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 评论选手
 * @author: taojian 
 * @since: 2018年10月12日 上午11:36:59 
 * @history:
 */
public class XN640005 extends AProcessor {

    private ICommentAO commentAO = SpringContextHolder
        .getBean(ICommentAO.class);

    private XN640005Req req;

    @Override
    public Object doBusiness() throws BizException {
        return commentAO.commentPlayer(req.getCode(), req.getContent(),
            req.getUserId());
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640005Req.class);
        ObjValidater.validateReq(req);
    }

}
