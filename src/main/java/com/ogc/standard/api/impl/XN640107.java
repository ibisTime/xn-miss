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
import com.ogc.standard.dto.req.XN640107Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 列表查会话
 * @author: taojian 
 * @since: 2018年10月10日 下午5:21:35 
 * @history:
 */
public class XN640107 extends AProcessor {

    private IMissSessionAO missSessionAO = SpringContextHolder
        .getBean(IMissSessionAO.class);

    private XN640107Req req;

    @Override
    public Object doBusiness() throws BizException {
        MissSession condition = new MissSession();
        condition.setType(req.getType());
        condition.setUser1(req.getUser1());
        condition.setCreateDatetimeStart(DateUtil.getFrontDate(
            req.getCreateDatetimeStart(), false));
        condition.setCreateDatetimeEnd(DateUtil.getFrontDate(
            req.getCreateDatetimeEnd(), true));

        String column = req.getOrderColumn();
        if (StringUtils.isBlank(column)) {
            column = "code";
        }
        condition.setOrder(column, req.getOrderDir());
        return missSessionAO.querySessionList(condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN640107Req.class);
        ObjValidater.validateReq(req);

    }

}
