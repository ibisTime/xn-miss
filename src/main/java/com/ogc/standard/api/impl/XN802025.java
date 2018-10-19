package com.ogc.standard.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.ogc.standard.ao.IBankcardAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.Bankcard;
import com.ogc.standard.dto.req.XN802025Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 分页查询银行卡
 * @author: lei 
 * @since: 2018年9月11日 下午5:38:29 
 * @history:
 */
public class XN802025 extends AProcessor {
    private IBankcardAO bankCardAO = SpringContextHolder
        .getBean(IBankcardAO.class);

    private XN802025Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Bankcard condition = new Bankcard();
        condition.setSystemCode(req.getSystemCode());
        condition.setUserId(req.getUserId());
        condition.setBankName(req.getBankName());
        condition.setBankcardNumber(req.getBankcardNumber());
        condition.setType(req.getType());
        condition.setStatus(req.getStatus());
        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = IBankcardAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return bankCardAO.queryBankcardPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802025Req.class);
    }

}
