package com.ogc.standard.api.impl;

import java.math.BigDecimal;

import com.ogc.standard.ao.ICollectAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.AmountUtil;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.ObjValidater;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dto.req.XN802362Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.enums.EOriginialCoin;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * BTC手动归集
 * @author: haiqingzheng 
 * @since: 2017年11月9日 下午7:00:49 
 * @history:
 */
public class XN802362 extends AProcessor {

    private ICollectAO collectAO = SpringContextHolder
        .getBean(ICollectAO.class);

    private XN802362Req req = null;

    /** 
     * @see com.cdkj.coin.wallet.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        BigDecimal balanceStart = AmountUtil.toBtc(StringValidater
            .toBigDecimal(req.getBalanceStart()));
        collectAO.collect(balanceStart, EOriginialCoin.BTC.getCode(), null);
        return new BooleanRes(true);
    }

    /** 
     * @see com.cdkj.coin.wallet.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802362Req.class);
        ObjValidater.validateReq(req);
    }

}
