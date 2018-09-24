package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IDivideDetailBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.IDivideDetailDAO;
import com.ogc.standard.domain.DivideDetail;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

@Component
public class DivideDetailBOImpl extends PaginableBOImpl<DivideDetail>
        implements IDivideDetailBO {

    @Autowired
    private IDivideDetailDAO divideDetailDAO;

    @Override
    public void saveDivideDetail(String userId, String currency,
            BigDecimal amount, String divideId) {

        DivideDetail data = new DivideDetail();
        data.setUserId(userId);
        data.setCurrency(currency);
        data.setAmount(amount);
        data.setCreateDatetime(new Date());
        data.setDivideId(divideId);

        divideDetailDAO.insert(data);
    }

    @Override
    public int refreshDivideDetail(DivideDetail data) {
        int count = 0;
        if (null != data) {
            count = divideDetailDAO.update(data);
        }
        return count;
    }

    @Override
    public List<DivideDetail> queryDivideDetailList(DivideDetail condition) {
        return divideDetailDAO.selectList(condition);
    }

    @Override
    public DivideDetail getDivideDetail(String id) {
        DivideDetail data = null;
        if (StringUtils.isNotBlank(id)) {
            DivideDetail condition = new DivideDetail();
            condition.setId(id);
            data = divideDetailDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.id_NOTEXIST.getCode());
            }
        }
        return data;
    }
}
