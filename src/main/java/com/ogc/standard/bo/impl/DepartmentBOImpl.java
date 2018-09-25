/**
 * @Title DepartmentBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月17日 下午9:08:05 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IDepartmentBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IDepartmentDAO;
import com.ogc.standard.domain.Department;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.exception.BizException;

/** 
 * @author: tao 
 * @since: 2018年8月17日 下午9:08:05 
 * @history:
 */
@Component
public class DepartmentBOImpl extends PaginableBOImpl<Department>
        implements IDepartmentBO {
    @Autowired
    private IDepartmentDAO departmentDAO;

    @Override
    public String saveDepartment(Department data) {

        if (data != null) {

            data.setCode(
                OrderNoGenerater.generate(EGeneratePrefix.BM.getCode()));
            data.setUpdateDatetime(new Date());
            departmentDAO.insert(data);
        }
        return data.getCode();
    }

    @Override
    public int removeDepartment(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Department condition = new Department();
            condition.setCode(code);
            Department department = departmentDAO.select(condition);
            if (department == null) {
                throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
            }
            count = departmentDAO.delete(condition);
        }
        return count;
    }

    @Override
    public int refreshDepartment(Department data) {

        int count = 0;
        if (data != null && StringUtils.isNotBlank(data.getCode())) {
            data.setUpdateDatetime(new Date());
            count = departmentDAO.update(data);
        }
        return count;
    }

    @Override
    public List<Department> queryDepartmentList(Department data) {
        return departmentDAO.selectList(data);
    }

    @Override
    public Department getDepartment(String code) {
        Department data = null;
        if (StringUtils.isNotBlank(code)) {
            Department condition = new Department();
            condition.setCode(code);
            data = departmentDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
            }
        }
        return data;
    }

    @Override
    public boolean isDepartmentExist(String code) {
        Department department = new Department();
        department.setCode(code);
        if (departmentDAO.selectTotalCount(department) >= 1) {
            return true;
        }
        return false;
    }

}
