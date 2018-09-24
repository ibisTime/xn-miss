package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IAdsAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.domain.Ads;
import com.ogc.standard.dto.req.XN625227Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 前端分页查询广告
 * @author: taojian 
 * @since: 2018年9月4日 下午8:18:19 
 * @history:
 */
public class XN625227 extends AProcessor {

    private IAdsAO adsAO = SpringContextHolder.getBean(IAdsAO.class);

    private XN625227Req req;

    @Override
    public Object doBusiness() throws BizException {

        Ads condition = new Ads();
        condition.setTradeCurrency(req.getTradeCurrency());
        condition.setTradeCoin(req.getCoin());
        condition.setTradeType(req.getTradeType());
        condition.setPayType(req.getPayType());
        condition.setMaxPrice(req.getMaxPrice());
        condition.setMinPrice(req.getMinPrice());
        return this.adsAO.frontPage(req.getStart(), req.getLimit(), condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {

        req = JsonUtil.json2Bean(inputparams, XN625227Req.class);
        ObjValidater.validateReq(req);
    }
}
