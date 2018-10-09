package com.ogc.standard.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.ogc.standard.ao.IAccountAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.Account;
import com.ogc.standard.dto.req.XN802501Req;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 列表查询账户
 * @author: xieyj 
 * @since: 2016年12月23日 下午8:32:58 
 * @history:
 */
public class XN803501 extends AProcessor {

    private IAccountAO accountAO = SpringContextHolder
        .getBean(IAccountAO.class);

    private XN802501Req req = null;

    /** 
    * @see com.xnjr.base.api.IProcessor#doBusiness()
    */
    @Override
    public Object doBusiness() throws BizException {
        Account condition = new Account();
        condition.setRealName(req.getRealName());
        condition.setType(req.getType());
        condition.setStatus(req.getStatus());
        condition.setCurrency(req.getCurrency());

        condition.setLastOrder(req.getLastOrder());
        condition.setCreateDatetimeStart(DateUtil.getFrontDate(
            req.getDateStart(), false));
        condition.setCreateDatetimeEnd(DateUtil.getFrontDate(req.getDateEnd(),
            true));
        condition.setSystemCode(req.getSystemCode());
        condition.setCompanyCode(req.getCompanyCode());

        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = IAccountAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        return accountAO.queryAccountList(condition);
    }

    /** 
    * @see com.xnjr.base.api.IProcessor#doCheck(java.lang.String)
    */
	@Override
	public void doCheck(String inputparams, String operator)
			throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN802501Req.class);
        StringValidater
            .validateBlank(req.getSystemCode(), req.getCompanyCode());
    }
}
