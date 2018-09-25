/**
 * @Title DepartmentAOImpl.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月18日 上午9:44:03 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IDepartmentAO;
import com.ogc.standard.bo.IDepartmentBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Department;
import com.ogc.standard.dto.req.XN630310Req;
import com.ogc.standard.dto.req.XN630312Req;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

/** 
 * @author: tao 
 * @since: 2018年8月18日 上午9:44:03 
 * @history:
 */
@Service
public class DepartmentAOImpl implements IDepartmentAO {

    @Autowired
    private IDepartmentBO departmentBO;

    @Override
    public String addDepartment(XN630310Req req) {
        Department data = new Department();
        data.setCompanyCode(req.getCompanyCode());
        data.setLeader(req.getLeader());
        data.setLeaderMobile(req.getLeaderMobile());
        data.setName(req.getName());
        data.setParentCode(req.getParentCode());
        data.setRemark(req.getRemark());
        data.setUpdater(req.getUpdater());
        return departmentBO.saveDepartment(data);
    }

    @Override
    public void dropDepartment(String code) {

        departmentBO.removeDepartment(code);
    }

    @Override
    public void editDepartment(XN630312Req req) {
        if (req != null && departmentBO.isDepartmentExist(req.getCode())) {
            Department data = new Department();
            data.setCode(req.getCode());
            data.setLeader(req.getLeader());
            data.setLeaderMobile(req.getLeaderMobile());
            data.setName(req.getName());
            data.setParentCode(req.getParentCode());
            data.setRemark(req.getRemark());

            data.setUpdater(req.getUpdater());
            departmentBO.refreshDepartment(data);
        } else {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }

    }

    @Override
    public Paginable<Department> queryDepartmentPage(int start, int limit,
            Department condition) {
        return departmentBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<Department> queryDepartmentList(Department condition) {
        return departmentBO.queryDepartmentList(condition);
    }

    @Override
    public Department getDepartment(String code) {
        if (!departmentBO.isDepartmentExist(code)) {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }
        return departmentBO.getDepartment(code);
    }

}
