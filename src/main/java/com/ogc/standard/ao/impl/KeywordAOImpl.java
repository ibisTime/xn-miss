package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IKeywordAO;
import com.ogc.standard.bo.IKeywordBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Keyword;
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

    @Override
    @Transactional
    public void addKeywords(List<XN628000Req> reqList, String updater) {
        for (XN628000Req req : reqList) {
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
        if (keywordBO.isKeywordExist(word)) {
            throw new BizException(EErrorCode_main.keyword_EXIST.getCode());
        }

        keywordBO.refreshKeyword(id, word, level, reaction, remark, updater);
    }

    @Override
    public Paginable<Keyword> queryKeywordPage(int start, int limit,
            Keyword condition) {
        return keywordBO.getPaginable(start, limit, condition);
    }

    @Override
    public Keyword getKeyword(Integer id) {
        return keywordBO.getKeyword(id);
    }
}
