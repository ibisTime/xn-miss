package com.ogc.standard.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.ogc.standard.ao.ISYSUserAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.SYSUser;
import com.ogc.standard.dto.req.XN630065Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 分页查询系统用户
 * @author: clockorange 
 * @since: Aug 7, 2018 10:05:42 AM 
 * @history:
 */
public class XN630065 extends AProcessor {

    private ISYSUserAO userAO = SpringContextHolder.getBean(ISYSUserAO.class);

    private XN630065Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        SYSUser condition = new SYSUser();
        condition.setDepartmentCode(req.getDepartmentCode());
        // condition.setMobileForQuery(req.getKeyword());
        // condition.setRealNameForQuery(req.getKeyword());
        condition.setLoginNameForQuery(req.getKeyword());
        condition.setRoleCode(req.getRoleCode());
        condition.setStatus(req.getStatus());
        condition.setUpdater(req.getUpdater());
        String column = req.getOrderColumn();
        condition.setCreateDatetimeStart(
            DateUtil.getFrontDate(req.getDateStart(), false));
        condition.setCreateDatetimeEnd(
            DateUtil.getFrontDate(req.getDateEnd(), true));
        if (StringUtils.isBlank(column)) {
            column = ISYSUserAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(column, req.getOrderDir());

        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());

        return userAO.querySYSUserPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN630065Req.class);
        ObjValidater.validateReq(req);
    }

}
