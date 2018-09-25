package com.ogc.standard.ao.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.ISYSMenuRoleAO;
import com.ogc.standard.bo.ISYSMenuBO;
import com.ogc.standard.bo.ISYSMenuRoleBO;
import com.ogc.standard.bo.ISYSRoleBO;
import com.ogc.standard.domain.SYSMenu;
import com.ogc.standard.domain.SYSMenuRole;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

@Service
public class SYSMenuRoleAOImpl implements ISYSMenuRoleAO {

    @Autowired
    ISYSMenuRoleBO sysMenuRoleBO;

    @Autowired
    ISYSMenuBO sysMenuBO;

    @Autowired
    ISYSRoleBO sysRoleBO;

    @Override
    @Transactional
    public int addSYSMenuRole(SYSMenuRole data) {
        int count = 0;
        if (!sysRoleBO.isSYSRoleExist(data.getRoleCode())) {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }
        // 删除角色所关联的菜单
        sysMenuRoleBO.removeSYSMenuList(data.getRoleCode());
        // 重新分配角色菜单
        if (CollectionUtils.isNotEmpty(data.getMenuCodeList())) {
            for (String sysMenuCode : data.getMenuCodeList()) {
                if (!sysMenuBO.isSYSMenuExist(sysMenuCode)) {
                    throw new BizException(
                        EErrorCode_main.code_NOTEXIST.getCode());
                }
                SYSMenuRole sysMenuRole = new SYSMenuRole();
                sysMenuRole.setMenuCode(sysMenuCode);
                sysMenuRole.setRoleCode(data.getRoleCode());
                sysMenuRole.setUpdater(data.getUpdater());
                sysMenuRole.setUpdateDatetime(new Date());
                sysMenuRole.setRemark(data.getRemark());
                sysMenuRole.setSystemCode(data.getSystemCode());
                count += sysMenuRoleBO.saveSYSMenuRole(sysMenuRole);
            }
        }
        return count;
    }

    @Override
    public List<SYSMenu> querySYSMenuList(SYSMenuRole data) {
        List<SYSMenu> list = sysMenuRoleBO.querySYSMenuList(data);
        ListSort(list);
        return list;
    }

    public void ListSort(List<SYSMenu> list) {
        Collections.sort(list, new Comparator<SYSMenu>() {
            @Override
            public int compare(SYSMenu o1, SYSMenu o2) {
                return o1.getOrderNo().compareTo(o2.getOrderNo());
            }
        });
    }
}
