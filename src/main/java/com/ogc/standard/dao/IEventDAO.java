/**
 * @Title ISmsDAO.java 
 * @Package com.ogc.standard.dao 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午1:28:36 
 * @version V1.0   
 */
package com.ogc.standard.dao;

import com.ogc.standard.dao.base.IBaseDAO;
import com.ogc.standard.domain.Event;

/** 
 * @author: dl 
 * @since: 2018年8月22日 下午1:28:36 
 * @history:
 */
public interface IEventDAO extends IBaseDAO<Event> {
    String NAMESPACE = IEventDAO.class.getName().concat(".");

    // 修改状态
    public int updateStatus(Event data);

    // 修改信息
    public int updateEvent(Event data);
}
