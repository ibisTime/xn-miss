package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.ICNavigateAO;
import com.ogc.standard.bo.ICNavigateBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.CNavigate;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

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

        navigate.setName(data.getName());
        navigate.setStatus(data.getStatus());
        navigate.setParentCode(data.getParentCode());
        navigate.setLocation(data.getLocation());
        navigate.setOrderNo(data.getOrderNo());
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
}
