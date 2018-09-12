package com.ogc.standard.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.ITokenEventDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.TokenEvent;

@Repository("tokenEventDAOImpl")
public class TokenEventDAOImpl extends AMybatisTemplate implements
        ITokenEventDAO {

    @Override
    public int insert(TokenEvent data) {
        return super.insert(NAMESPACE.concat("insert_tokenEvent"), data);
    }

    @Override
    public int delete(TokenEvent data) {
        return super.delete(NAMESPACE.concat("delete_tokenEvent"), data);
    }

    @Override
    public TokenEvent select(TokenEvent condition) {
        return super.select(NAMESPACE.concat("select_tokenEvent"), condition,
            TokenEvent.class);
    }

    @Override
    public long selectTotalCount(TokenEvent condition) {
        return super.selectTotalCount(
            NAMESPACE.concat("select_tokenEvent_count"), condition);
    }

    @Override
    public List<TokenEvent> selectList(TokenEvent condition) {
        return super.selectList(NAMESPACE.concat("select_tokenEvent"),
            condition, TokenEvent.class);
    }

    @Override
    public List<TokenEvent> selectList(TokenEvent condition, int start,
            int count) {
        return super.selectList(NAMESPACE.concat("select_tokenEvent"), start,
            count, condition, TokenEvent.class);
    }

    @Override
    public void insertEventList(List<TokenEvent> tokenEventList) {
        super.insertBatch(NAMESPACE.concat("insert_eventList"),
            (List) tokenEventList);
    }

}
