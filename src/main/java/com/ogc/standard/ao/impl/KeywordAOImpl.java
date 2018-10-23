package com.ogc.standard.ao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IKeywordAO;
import com.ogc.standard.bo.IKeywordBO;
import com.ogc.standard.bo.ISYSUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Keyword;
import com.ogc.standard.domain.SYSUser;
import com.ogc.standard.dto.req.XN628000Req;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

/**
 * @author: silver 
 * @since: 2018年8月22日 上午10:52:40 
 * @history:
 */
@Service
public class KeywordAOImpl implements IKeywordAO {

    @Autowired
    private IKeywordBO keywordBO;

    @Autowired
    private ISYSUserBO sysUserBO;

    @Override
    @Transactional
    public void addKeywords(List<XN628000Req> reqList, String updater) {
        for (XN628000Req req : reqList) {
            if (keywordBO.isKeywordExist(req.getWord())) {
                throw new BizException(EErrorCode_main.keyword_EXIST.getCode());
            }
            keywordBO.saveKeyword(req.getWord(), req.getLevel(),
                req.getReaction(), req.getRemark(), updater);
        }
    }

    @Override
    public void addKeyword(String word, String level, String reaction,
            String remark, String updater) {
        if (keywordBO.isKeywordExist(word)) {
            throw new BizException(EErrorCode_main.keyword_EXIST.getCode());
        }

        keywordBO.saveKeyword(word, level, reaction, remark, updater);
    }

    @Override
    public void dropKeyword(Integer id) {
        keywordBO.removeKeyword(id);
    }

    @Override
    public void editKeyword(Integer id, String word, String level,
            String reaction, String remark, String updater) {
        keywordBO.refreshKeyword(id, word, level, reaction, remark, updater);
    }

    @Override
    public Paginable<Keyword> queryKeywordPage(int start, int limit,
            Keyword condition) {
        Paginable<Keyword> paginable = keywordBO.getPaginable(start, limit,
            condition);
        List<Keyword> list = paginable.getList();
        for (Keyword data : list) {
            init(data);
        }
        return paginable;
    }

    @Override
    public Keyword getKeyword(Integer id) {
        Keyword data = keywordBO.getKeyword(id);
        init(data);
        return data;
    }

    public void init(Keyword data) {
        if (StringUtils.isNotBlank(data.getUpdater())) {
            SYSUser sysUser = sysUserBO.getSYSUserUnCheck(data.getUpdater());
            data.setSysUser(sysUser);
        }
    }
}
