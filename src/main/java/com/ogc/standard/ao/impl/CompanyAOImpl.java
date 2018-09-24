/**
 * @Title CompanyAOImpl.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月17日 下午5:55:44 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.ICompanyAO;
import com.ogc.standard.bo.ICompanyBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Company;
import com.ogc.standard.dto.req.XN630300Req;
import com.ogc.standard.dto.req.XN630302Req;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

/** 
 * @author: tao 
 * @since: 2018年8月17日 下午5:55:44 
 * @history:
 */
@Service
public class CompanyAOImpl implements ICompanyAO {

    @Autowired
    private ICompanyBO companyBO;

    @Override
    public String addCompany(XN630300Req req) {
        Company data = new Company();
        data.setName(req.getName());
        data.setUpdater(req.getUpdater());
        data.setRemark(req.getRemark());
        return companyBO.saveCompany(data);
    }

    @Override
    public void dropCompany(String code) {

        companyBO.removeCompany(code);
    }

    @Override
    public void editCompany(XN630302Req req) {
        if (req != null && companyBO.isCompanyExist(req.getCode())) {
            Company company = companyBO.getCompany(req.getCode());
            company.setName(req.getName());
            company.setUpdater(req.getUpdater());
            company.setUpdateDatetime(new Date());
            company.setRemark(req.getRemark());
            companyBO.refreshCompany(company);
        } else {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }

    }

    @Override
    public Paginable<Company> queryCompanyPage(int start, int limit,
            Company condition) {
        return companyBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<Company> queryCompanyList(Company condition) {
        return companyBO.queryCompanyList(condition);
    }

    @Override
    public Company getCompany(String code) {
        return companyBO.getCompany(code);
    }

}
