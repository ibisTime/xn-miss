package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.ICNavigateAO;
import com.ogc.standard.bo.ICNavigateBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.CNavigate;
import com.ogc.standard.enums.ECnavigateStauts;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;

@Service
public class CNavigateAOImpl implements ICNavigateAO {

    @Autowired
    private ICNavigateBO cNavigateBO;

    @Override
    public String addCNavigate(CNavigate data) {
        return cNavigateBO.saveCNavigate(data);
    }

    @Override
    public void dropCNavigate(String code) {
        if (!cNavigateBO.isCNavigateExist(code)) {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }
        cNavigateBO.removeCNavigate(code);
    }

    @Override
    public void editCNavigate(CNavigate data) {
        CNavigate navigate = cNavigateBO.getCNavigate(data.getCode());
        if (ECnavigateStauts.ON.equals(navigate.getStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前记录已上架，请先下架");
        }
        navigate.setName(data.getName());
        navigate.setParentCode(data.getParentCode());
        navigate.setRemark(data.getRemark());
        navigate.setUrl(data.getUrl());
        navigate.setPic(data.getPic());
        cNavigateBO.refreshCNavigate(navigate);
    }

    @Override
    public Paginable<CNavigate> queryCNavigatePage(int start, int limit,
            CNavigate condition) {
        return cNavigateBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<CNavigate> queryCNavigateList(CNavigate condition) {
        return cNavigateBO.queryCNavigateList(condition);
    }

    @Override
    public CNavigate getCNavigate(String code) {
        return cNavigateBO.getCNavigate(code);
    }

    @Override
    public void Release(String code, String location, Integer orderNo,
            String remark) {
        CNavigate data = cNavigateBO.getCNavigate(code);
        if (!ECnavigateStauts.DRAFT.getCode().equals(data.getStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "处于无法上架的状态");
        }
        cNavigateBO.refreshStatus(code, ECnavigateStauts.ON.getCode(),
            location, orderNo, remark);
    }

    @Override
    public void obtain(String code, String remark) {
        CNavigate data = cNavigateBO.getCNavigate(code);
        if (!ECnavigateStauts.ON.getCode().equals(data.getStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "处于无法下架的状态");
        }
        cNavigateBO.refreshStatus(code, ECnavigateStauts.OFF.getCode(), null,
            null, remark);
    }
}
