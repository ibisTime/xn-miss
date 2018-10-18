/**
 * @Title ActionDAOImpl.java 
 * @Package com.ogc.standard.dao.impl 
 * @Description 
 * @author taojian  
 * @date 2018年10月12日 下午2:05:03 
 * @version V1.0   
 */
package com.ogc.standard.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.IActionDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.Action;

/** 
 * @author: taojian 
 * @since: 2018年10月12日 下午2:05:03 
 * @history:
 */
@Repository("actionDAOImpl")
public class ActionDAOImpl extends AMybatisTemplate implements IActionDAO {

    @Override
    public int insert(Action data) {
        return super.insert(NAMESPACE.concat("insert_action"), data);
    }

    @Override
    public int delete(Action data) {
        return super.delete(NAMESPACE.concat("delete"), data);
    }

    @Override
    public Action select(Action condition) {
        return super.select(NAMESPACE.concat("select_action"), condition,
            Action.class);
    }

    @Override
    public long selectTotalCount(Action condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_action_count"),
            condition);
    }

    @Override
    public List<Action> selectList(Action condition) {
        return super.selectList(NAMESPACE.concat("select_action"), condition,
            Action.class);
    }

    @Override
    public List<Action> selectList(Action condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_action"), start,
            count, condition, Action.class);
    }

}
