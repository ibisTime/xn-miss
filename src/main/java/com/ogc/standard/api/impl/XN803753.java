package com.ogc.standard.api.impl;

import java.math.BigDecimal;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.ogc.standard.ao.IWithdrawAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.dto.req.XN803753Req;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.enums.ESystemCode;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 批量回录线下取现订单
 * @author: myb858 
 * @since: 2017年4月24日 下午7:52:49 
 * @history:
 */
public class XN803753 extends AProcessor {
    private IWithdrawAO withdrawAO = SpringContextHolder
        .getBean(IWithdrawAO.class);

    private XN803753Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        for (String code : req.getCodeList()) {
            BigDecimal payFee = BigDecimal.ZERO;
            if (StringUtils.isNotBlank(req.getPayFee())) {
                payFee = new BigDecimal(req.getPayFee());
            }
            withdrawAO.payOrder(code, req.getPayUser(), req.getPayResult(),
                req.getPayNote(), req.getChannelOrder(), payFee,
                ESystemCode.MISS.getCode());
        }
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN803753Req.class);
        if (CollectionUtils.isEmpty(req.getCodeList())) {
            throw new BizException("订单列表不能为空");
        }
        StringValidater.validateBlank(req.getPayUser(), req.getPayResult(),
            req.getPayNote());
    }

}
