/**
 * @Title Read.java 
 * @Package com.ogc.standard.domain 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午5:51:21 
 * @version V1.0   
 */
package com.ogc.standard.domain;

import java.util.Date;

import com.ogc.standard.dao.base.ABaseDO;

/** 
 * 阅读消息
 * @author: dl 
 * @since: 2018年8月22日 下午5:51:21 
 * @history:
 */
public class Read extends ABaseDO {

    private static final long serialVersionUID = -8738630231714278522L;

    // ***********db properties***********
    // id
    private Long id;

    // 用户编号
    private String userId;

    // 对象类型
    private String toType;

    // 对象编号
    private String toCode;

    // 状态 0-未阅读 1-已阅读 2-已删除
    private String status;

    // 推送时间
    private Date createDatetime;

    // ***********db properties***********

    private Event eventInfo;

    public Event getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(Event eventInfo) {
        this.eventInfo = eventInfo;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
