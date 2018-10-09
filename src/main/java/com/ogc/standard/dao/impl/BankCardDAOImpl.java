package com.ogc.standard.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.IBankCardDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.Bankcard;

/**
 * 
 * @author: lei 
 * @since: 2018年9月11日 下午5:41:56 
 * @history:
 */
@Repository("bankcardDAOImpl")
public class BankCardDAOImpl extends AMybatisTemplate implements IBankCardDAO {

    @Override
    public int insert(Bankcard data) {
        return super.insert(NAMESPACE.concat("insert_bankcard"), data);
    }

    @Override
    public int delete(Bankcard data) {
        return super.delete(NAMESPACE.concat("delete_bankcard"), data);
    }

    @Override
    public Bankcard select(Bankcard condition) {
        return super.select(NAMESPACE.concat("select_bankcard"), condition,
            Bankcard.class);
    }

    @Override
    public long selectTotalCount(Bankcard condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_bankcard_count"),
            condition);
    }

    @Override
    public List<Bankcard> selectList(Bankcard condition) {
        return super.selectList(NAMESPACE.concat("select_bankcard"), condition,
            Bankcard.class);
    }

    @Override
    public List<Bankcard> selectList(Bankcard condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_bankcard"), start,
            count, condition, Bankcard.class);
    }

    @Override
    public int update(Bankcard data) {
        return super.update(NAMESPACE.concat("update_bankcard"), data);
    }

}
