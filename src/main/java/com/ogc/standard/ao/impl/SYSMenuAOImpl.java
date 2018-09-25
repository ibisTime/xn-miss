package com.ogc.standard.ao.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.ISYSMenuAO;
import com.ogc.standard.bo.ISYSMenuBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.SYSMenu;
import com.ogc.standard.dto.req.XN630010Req;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

@Service
public class SYSMenuAOImpl implements ISYSMenuAO {

    @Autowired
    ISYSMenuBO sysMenuBO;

    @Override
    public String addSYSMenu(XN630010Req req) {
        SYSMenu data = new SYSMenu();
        if (req != null) {
            // 判断父编号是否存在

            data.setName(req.getName());
            data.setType(req.getType());
            data.setUrl(req.getUrl());
            data.setParentCode(req.getParentCode());
            data.setOrderNo(req.getOrderNo());
            data.setUpdater(req.getUpdater());
            data.setRemark(req.getRemark());
            data.setSystemCode(req.getSystemCode());
            if (!"0".equalsIgnoreCase(data.getParentCode())
                    && !sysMenuBO.isSYSMenuExist(data.getParentCode())) {
                throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
            }
            sysMenuBO.saveSYSMenu(data);
        } else {
            throw new BizException(EErrorCode_main.code_EXIST.getCode());
        }
        return data.getCode();
    }

    @Override
    public boolean dropSYSMenu(String code) {
        if (!sysMenuBO.isSYSMenuExist((code))) {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }
        sysMenuBO.removeSYSMenu(code);
        return true;
    }

    @Override
    public boolean editSYSMenu(SYSMenu data) {
        if (data != null && sysMenuBO.isSYSMenuExist(data.getCode())) {
            sysMenuBO.refreshSYSMenu(data);
        } else {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }
        return true;
    }

    @Override
    public Paginable<SYSMenu> querySYSMenuPage(int start, int limit,
            SYSMenu condition) {
        return sysMenuBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<SYSMenu> querySYSMenuList(SYSMenu condition) {
        List<SYSMenu> menuList = sysMenuBO.querySYSMenuList(condition);
        ListSort(menuList);
        return menuList;
    }

    public void ListSort(List<SYSMenu> list) {
        Collections.sort(list, new Comparator<SYSMenu>() {
            @Override
            public int compare(SYSMenu o1, SYSMenu o2) {
                return o1.getOrderNo().compareTo(o2.getOrderNo());
            }
        });
    }

    @Override
    public SYSMenu getSYSMenu(String code) {
        if (!sysMenuBO.isSYSMenuExist(code)) {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }
        SYSMenu condition = new SYSMenu();
        condition.setCode(code);
        return sysMenuBO.getSYSMenu(code);
    }
}
