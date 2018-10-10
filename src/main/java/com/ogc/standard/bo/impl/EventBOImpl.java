/**
 * @Title SmsBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午2:14:47 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IEventBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IEventDAO;
import com.ogc.standard.domain.Event;
import com.ogc.standard.dto.req.XN805300Req;
import com.ogc.standard.dto.req.XN805301Req;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.exception.BizException;

/** 
 * @author: dl 
 * @since: 2018年8月22日 下午2:14:47 
 * @history:
 */
@Component
public class EventBOImpl extends PaginableBOImpl<Event> implements IEventBO {

    @Autowired
    private IEventDAO eventDAO;

    @Override
    public boolean isEventExit(String code) {
        Event event = new Event();
        event.setCode(code);
        if (eventDAO.selectTotalCount(event) >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public String saveEvent(XN805300Req req, String status) {
        Event data = new Event();
        Date now = new Date();
        String code = OrderNoGenerater
            .generate(EGeneratePrefix.EVENT.getCode());
        data.setCode(code);
        data.setTitle(req.getTitle());
        data.setContent(req.getContent());
        data.setStatus(status);
        data.setCreateDatetime(now);
        data.setUpdater(req.getUpdater());
        data.setUpdateDatetime(now);
        eventDAO.insert(data);
        return data.getCode();
    }

    @Override
    public int refreshEvent(XN805301Req req, String status) {
        int count = 0;
        Event data = getEvent(req.getCode());
        data.setTitle(req.getTitle());
        data.setContent(req.getContent());
        data.setStatus(status);
        data.setUpdater(req.getUpdater());
        data.setUpdateDatetime(new Date());
        data.setRemark(req.getRemark());
        count = eventDAO.updateEvent(data);
        return count;
    }

    @Override
    public int refreshStatus(String code, String status, String updater,
            String remark) {
        Event data = getEvent(code);
        data.setStatus(status);
        data.setUpdater(updater);
        data.setRemark(remark);
        data.setUpdateDatetime(new Date());
        return eventDAO.updateStatus(data);
    }

    @Override
    public List<Event> queryEventList(Event data) {
        return eventDAO.selectList(data);
    }

    @Override
    public Event getEvent(String code) {
        Event data = null;
        if (StringUtils.isNotBlank(code)) {
            Event condition = new Event();
            condition.setCode(code);
            data = eventDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.sms_NOTEXIST.getCode());
            }
        }
        return data;
    }

}
