/**
 * @Title ISmsBO.java 
 * @Package com.ogc.standard.bo 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午2:04:01 
 * @version V1.0   
 */
package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Event;
import com.ogc.standard.dto.req.XN805300Req;
import com.ogc.standard.dto.req.XN805301Req;

/** 
 * @author: dl 
 * @since: 2018年8月22日 下午2:04:01 
 * @history:
 */
public interface IEventBO extends IPaginableBO<Event> {

    public boolean isEventExit(String code);

    public String saveEvent(XN805300Req req, String status);

    public int refreshEvent(XN805301Req req, String status);

    public int refreshStatus(String code, String status, String updater,
            String remark);

    public List<Event> queryEventList(Event data);

    public Event getEvent(String code);
}
