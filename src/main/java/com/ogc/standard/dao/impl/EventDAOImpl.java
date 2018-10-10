/**
 * @Title SmsDAOImpl.java 
 * @Package com.ogc.standard.dao.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午1:32:00 
 * @version V1.0   
 */
package com.ogc.standard.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.IEventDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.Event;

/** 
 * @author: taojian 
 * @since: 2018年10月10日 上午11:42:46 
 * @history:
 */
@Repository("eventDAOImpl")
public class EventDAOImpl extends AMybatisTemplate implements IEventDAO {

    @Override
    public int insert(Event data) {
        return super.insert(NAMESPACE.concat("insert_event"), data);
    }

    @Override
    public int delete(Event data) {
        return 0;
    }

    @Override
    public Event select(Event condition) {
        return super.select(NAMESPACE.concat("select_event"), condition,
            Event.class);
    }

    @Override
    public long selectTotalCount(Event condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_event_count"),
            condition);
    }

    @Override
    public List<Event> selectList(Event condition) {
        return super.selectList(NAMESPACE.concat("select_event"), condition,
            Event.class);
    }

    @Override
    public List<Event> selectList(Event condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_event"), start, count,
            condition, Event.class);

    }

    @Override
    public int updateStatus(Event data) {
        return super.update(NAMESPACE.concat("update_status"), data);
    }

    @Override
    public int updateEvent(Event data) {
        return super.update(NAMESPACE.concat("update_event"), data);
    }

}
