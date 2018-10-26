/**
 * @Title XN640100.java 
 * @Package com.ogc.standard.api.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午5:21:35 
 * @version V1.0   
 */
package com.ogc.standard.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.ogc.standard.ao.IMissSessionAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.domain.MissSession;
import com.ogc.standard.dto.req.XN640105Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 分页查会话
 * @author: taojian 
 * @since: 2018年10月10日 下午5:21:35 
 * @history:
 */
public class XN640105 extends AProcessor {

    private IMissSessionAO missSessionAO = SpringContextHolder
        .getBean(IMissSessionAO.class);

    private XN640105Req req;

    @Override
    public Object doBusiness() throws BizException {
        MissSession condition = new MissSession();
        condition.setType(req.getType());
        condition.setUser1(req.getUser1());
        condition.setUser2(req.getUser2());
        condition.setCreateDatetimeStart(DateUtil.getFrontDate(
            req.getCreateDatetimeStart(), false));
        condition.setCreateDatetimeEnd(DateUtil.getFrontDate(
            req.getCreateDatetimeEnd(), true));

        String column = req.getOrderColumn();
        if (StringUtils.isBlank(column)) {
            column = "code";
        }
        condition.setOrder(column, req.getOrderDir());
        int start = Integer.valueOf(req.getStart());
        int limit = Integer.valueOf(req.getLimit());
        return missSessionAO.querySessionPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640105Req.class);
        ObjValidater.validateReq(req);

    }

}
