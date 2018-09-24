package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ISYSRoleBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.ISYSRoleDAO;
import com.ogc.standard.domain.SYSRole;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.exception.BizException;

@Component
public class SYSRoleBOImpl extends PaginableBOImpl<SYSRole>
        implements ISYSRoleBO {

    @Autowired
    private ISYSRoleDAO sysRoleDAO;

    @Override
    public boolean isSYSRoleExist(String code) {
        SYSRole role = new SYSRole();
        role.setCode(code);
        if (sysRoleDAO.selectTotalCount(role) >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public int saveSYSRole(SYSRole data) {
        int count = 0;
        if (data != null) {
            data.setCode(
                OrderNoGenerater.generate(EGeneratePrefix.JS.getCode()));
            data.setUpdateDatetime(new Date());
            count = sysRoleDAO.insert(data);
        }
        return count;
    }

    @Override
    public int removeSYSRole(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            SYSRole data = new SYSRole();
            data.setCode(code);
            count = sysRoleDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshSYSRole(SYSRole data) {
        int count = 0;
        if (data != null && StringUtils.isNotBlank(data.getCode())) {
            data.setUpdateDatetime(new Date());
            count = sysRoleDAO.update(data);
        }
        return count;
    }

    @Override
    public List<SYSRole> querySYSRoleList(SYSRole data) {
        return sysRoleDAO.selectList(data);
    }

    @Override
    public SYSRole getSYSRole(String code) {
        SYSRole data = null;
        if (StringUtils.isNotBlank(code)) {
            SYSRole condition = new SYSRole();
            condition.setCode(code);
            data = sysRoleDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
            }
        }
        return data;
    }
}
