package com.ogc.standard.ao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IJourHistoryAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IHLOrderBO;
import com.ogc.standard.bo.IJourHistoryBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.domain.Jour;
import com.ogc.standard.exception.BizException;

/** 
 * @author: xieyj 
 * @since: 2016年12月23日 下午9:16:58 
 * @history:
 */
@Service
public class JourHistoryAOImpl implements IJourHistoryAO {

    @Autowired
    private IJourHistoryBO jourHistoryBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IHLOrderBO hlOrderBO;

    @Override
    public Paginable<Jour> queryJourPage(int start, int limit, Jour condition) {
        String bizType = condition.getBizType();
        if (StringUtils.isNotBlank(bizType)) {
            String[] bizTypeArrs = bizType.split(",");
            List<String> bizTypeList = new ArrayList<String>();
            for (int i = 0; i < bizTypeArrs.length; i++) {
                bizTypeList.add(bizTypeArrs[i]);
            }
            condition.setBizType(null);
            condition.setBizTypeList(bizTypeList);
        }
        return jourHistoryBO.getPaginable(start, limit, condition);
    }

    @Override
    public Paginable<Jour> queryFrontJourPage(int start, int limit,
            Jour condition) {
        if (DateUtil.daysBetween(condition.getCreateDatetimeStart(),
            condition.getCreateDatetimeEnd()) >= 7) {
            throw new BizException("xn702000", "请选择7天内查询时间");
        }
        String bizType = condition.getBizType();
        if (StringUtils.isNotBlank(bizType)) {
            String[] bizTypeArrs = bizType.split(",");
            List<String> bizTypeList = new ArrayList<String>();
            for (int i = 0; i < bizTypeArrs.length; i++) {
                bizTypeList.add(bizTypeArrs[i]);
            }
            condition.setBizType(null);
            condition.setBizTypeList(bizTypeList);
        }
        return jourHistoryBO.getPaginable(start, limit, condition);
    }

    @Override
    public Jour getJour(String code, String systemCode) {
        return jourHistoryBO.getJour(code, systemCode);
    }
}
