/**
 * @Title ISmsAO.java 
 * @Package com.ogc.standard.ao 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午2:51:40 
 * @version V1.0   
 */
package com.ogc.standard.ao;

import java.util.List;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Event;
import com.ogc.standard.dto.req.XN805300Req;
import com.ogc.standard.dto.req.XN805301Req;

/** 
 * @author: dl 
 * @since: 2018年8月22日 下午2:51:40 
 * @history:
 */
public interface IEventAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    // 新增赛事信息（草稿或发布）
    public String addEvent(XN805300Req req);

    // 修改赛事信息（草稿或发布）
    public void editEvent(XN805301Req req);

    // 审批
    public void approveEvent(String code, String approver,
            String approveResult, String remark);

    // 上架
    public void releaseEvent(String code, String updater, String remark);

    // 下架
    public void obtainEvent(String code, String updater, String remark);

    public Paginable<Event> queryEventPage(int start, int limit, Event condition);

    public List<Event> queryEventList(Event condition);

    public Event getEvent(String code);
}
