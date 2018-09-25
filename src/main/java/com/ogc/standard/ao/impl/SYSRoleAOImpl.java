package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.ISYSRoleAO;
import com.ogc.standard.bo.ISYSMenuRoleBO;
import com.ogc.standard.bo.ISYSRoleBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.SYSRole;
import com.ogc.standard.dto.req.XN630000Req;
import com.ogc.standard.dto.req.XN630002Req;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

@Service
public class SYSRoleAOImpl implements ISYSRoleAO {

    @Autowired
    ISYSRoleBO sysRoleBO;

    @Autowired
    ISYSMenuRoleBO sysMenuRoleBO;

    @Override
    public String addSYSRole(XN630000Req req) {
        SYSRole data = new SYSRole();
        data.setName(req.getName());
        data.setUpdater(req.getUpdater());
        data.setRemark(req.getRemark());
        data.setSystemCode(req.getSystemCode());
        sysRoleBO.saveSYSRole(data);
        return data.getCode();
    }

    @Override
    @Transactional
    public boolean dropSYSRole(String roleCode) {
        if (!sysRoleBO.isSYSRoleExist(roleCode)) {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }
        // User condition = new User();
        // condition.setRoleCode(roleCode);
        // List<User> list = userBO.queryUserList(condition);
        // if (!CollectionUtils.sizeIsEmpty(list)) {
        // throw new BizException("lh4000", "该角色已在使用，无法删除！");
        // }
        // 删除角色和角色菜单关联表
        sysRoleBO.removeSYSRole(roleCode);
        sysMenuRoleBO.removeSYSMenuList(roleCode);
        return true;
    }

    @Override
    public boolean editSYSRole(XN630002Req req) {
        if (req != null && sysRoleBO.isSYSRoleExist(req.getCode())) {
            SYSRole data = new SYSRole();
            data.setCode(req.getCode());
            data.setName(req.getName());
            data.setUpdater(req.getUpdater());
            data.setRemark(req.getRemark());
            sysRoleBO.refreshSYSRole(data);
        } else {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }
        return true;
    }

    @Override
    public List<SYSRole> querySYSRoleList(SYSRole condition) {
        return sysRoleBO.querySYSRoleList(condition);
    }

    @Override
    public Paginable<SYSRole> querySYSRolePage(int start, int limit,
            SYSRole condition) {
        return sysRoleBO.getPaginable(start, limit, condition);
    }

    /** 
     * @see com.std.user.ao.ISYSRoleAO#getSYSRole(java.lang.String)
     */
    @Override
    public SYSRole getSYSRole(String code) {
        return sysRoleBO.getSYSRole(code);
    }
}
