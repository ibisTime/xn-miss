/**
 * @Title CompanyBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月17日 下午5:03:29 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ICompanyBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.ICompanyDAO;
import com.ogc.standard.domain.Company;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.exception.BizException;

/** 
 * @author: tao 
 * @since: 2018年8月17日 下午5:03:29 
 * @history:
 */
@Component
public class CompanyBOImpl extends PaginableBOImpl<Company>
        implements ICompanyBO {

    @Autowired
    private ICompanyDAO companyDAO;

    @Override
    public long getTotalCount(Company condition) {
        return 0;
    }

    @Override
    public Paginable<Company> getPaginable(int start, Company condition) {
        return null;
    }

    @Override
    public Paginable<Company> getPaginable(int start, int pageSize,
            Company condition) {
        return null;
    }

    @Override
    public String saveCompany(Company data) {
        if (data != null) {

            data.setCode(
                OrderNoGenerater.generate(EGeneratePrefix.GS.getCode()));
            data.setUpdateDatetime(new Date());
            data.setCreatDatetime(new Date());
            companyDAO.insert(data);
        }
        return data.getCode();
    }

    @Override
    public int removeCompany(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Company condition = new Company();
            condition.setCode(code);
            Company company = companyDAO.select(condition);
            if (company == null) {
                throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
            }
            count = companyDAO.delete(condition);

        }
        return count;
    }

    @Override
    public int refreshCompany(Company data) {
        int count = 0;
        if (data != null && StringUtils.isNotBlank(data.getCode())) {
            data.setUpdateDatetime(new Date());
            count = companyDAO.update(data);
        }
        return count;
    }

    @Override
    public List<Company> queryCompanyList(Company data) {
        return companyDAO.selectList(data);
    }

    @Override
    public Company getCompany(String code) {
        Company data = null;
        if (StringUtils.isNotBlank(code)) {
            Company condition = new Company();
            condition.setCode(code);
            data = companyDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
            }
        }
        return data;
    }

    @Override
    public boolean isCompanyExist(String code) {
        Company company = new Company();
        company.setCode(code);
        if (companyDAO.selectTotalCount(company) >= 1) {
            return true;
        }
        return false;
    }

}
