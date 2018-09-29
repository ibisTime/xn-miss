package com.ogc.standard.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.ogc.standard.ao.IUserAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.domain.User;
import com.ogc.standard.dto.req.XN805120Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 分页查询用户列表
 * @author: xieyj 
 * @since: 2017年7月16日 下午4:38:56 
 * @history:
 */
public class XN805120 extends AProcessor {
    private IUserAO userAO = SpringContextHolder.getBean(IUserAO.class);

    private XN805120Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        User condition = new User();
        condition.setLoginName(req.getLoginName());
        condition.setMobileForQuery(req.getMobile());
        condition.setKind(req.getKind());
        condition.setNicknameForQuery(req.getNickname());
        condition.setLevel(req.getLevel());
        condition.setUserReferee(req.getUserReferee());

        condition.setIdKind(req.getIdKind());
        condition.setIdNo(req.getIdNo());
        // condition.setRealName(req.getRealName());
        condition.setRealNameForQuery(req.getRealName());
        condition.setStatus(req.getStatus());

        condition.setProvince(req.getProvince());
        condition.setCity(req.getCity());
        condition.setArea(req.getArea());
        condition.setLongitude(req.getLongitude());
        condition.setLatitude(req.getLatitude());
        condition.setCreateDatetimeStart(
            DateUtil.getFrontDate(req.getCreateDatetimeStart(), false));
        condition.setCreateDatetimeEnd(
            DateUtil.getFrontDate(req.getCreateDatetimeEnd(), true));

        condition.setUpdater(req.getUpdater());
        String column = req.getOrderColumn();
        if (StringUtils.isBlank(column)) {
            column = IUserAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(column, req.getOrderDir());
        int start = Integer.valueOf(req.getStart());
        int limit = Integer.valueOf(req.getLimit());
        Paginable<User> paginable = userAO.queryUserPage(start, limit,
            condition);

        return paginable;
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805120Req.class);
        ObjValidater.validateReq(req);
    }
}
