package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IBlacklistAO;
import com.ogc.standard.bo.IBlacklistBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Blacklist;
import com.ogc.standard.domain.User;
import com.ogc.standard.enums.EBlacklistStatus;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EUserStatus;
import com.ogc.standard.exception.BizException;

@Service
public class BlacklistAOImpl implements IBlacklistAO {

    @Autowired
    private IBlacklistBO blacklistBO;

    @Autowired
    private IUserBO userBO;

    @Override
    public void addBlacklist(String userId, String type, String updater,
            String remark) {
        User user = userBO.getUser(userId);
        blacklistBO.isAddBlacklist(userId);
        blacklistBO.saveBlacklist(user, type, updater, remark);
        // 修改用户状态为人工锁定
        userBO.refreshStatus(userId, EUserStatus.Ren_Locked, updater, remark);

    }

    @Override
    public int editBlacklist(Long id, String updater, String remark) {
        int count = 0;
        Blacklist blacklist = blacklistBO.getBlacklist(id);
        if (EBlacklistStatus.VALID.getCode().equals(blacklist.getStatus())) {
            count = blacklistBO.refreshBlacklist(id, updater, remark);
        } else {
            throw new BizException(EErrorCode_main.bla_NOTEXIST.getCode());
        }
        return count;
    }

    @Override
    public Paginable<Blacklist> queryBlacklistPage(int start, int limit,
            Blacklist condition) {
        Paginable<Blacklist> results = blacklistBO.getPaginable(start, limit,
            condition);
        return results;
    }

    @Override
    public List<Blacklist> queryBlacklistList(Blacklist condition) {
        return blacklistBO.queryBlacklistList(condition);
    }

    @Override
    public Blacklist getBlacklist(Long id) {
        return blacklistBO.getBlacklist(id);
    }

}
