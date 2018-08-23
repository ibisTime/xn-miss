/**
 * @Title XN805310.java 
 * @Package com.ogc.standard.api.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午9:24:33 
 * @version V1.0   
 */
package com.ogc.standard.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.ogc.standard.ao.IReadAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.Read;
import com.ogc.standard.dto.req.XN805315Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 分页查询我的消息
 * @author: dl 
 * @since: 2018年8月22日 下午9:24:33 
 * @history:
 */
public class XN805315 extends AProcessor {

    private IReadAO readAO = SpringContextHolder.getBean(IReadAO.class);

    private XN805315Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Read condition = new Read();
        condition.setReceiveWay(req.getReceiveWay());
        condition.setStatus(req.getStatus());
        condition.setUserId(req.getUserId());
        String column = req.getOrderColumn();
        if (StringUtils.isBlank(column)) {
            column = IReadAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(column, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return readAO.queryReadPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805315Req.class);
        ObjValidater.validateReq(req);
    }

}
