/**
 * @Title Session.java 
 * @Package com.ogc.standard.domain 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午3:22:00 
 * @version V1.0   
 */
package com.ogc.standard.domain;

import java.util.Date;
import java.util.List;

import com.ogc.standard.dao.base.ABaseDO;

/** 
 * 会话
 * @author: taojian 
 * @since: 2018年10月10日 下午3:22:00 
 * @history:
 */
public class MissSession extends ABaseDO {

    private static final long serialVersionUID = 7540253521864819742L;

    // ***********db properties***********
    private String code;

    private String type;

    private String user1;

    private String user2;

    private Date createDatetime;

    private Long unreadSum;

    // ***********db properties***********

    private String user1Nickname;

    private Date createDatetimeStart;

    private Date createDatetimeEnd;

    private List<Question> dataList;

    public List<Question> getDataList() {
        return dataList;
    }

    public void setDataList(List<Question> dataList) {
        this.dataList = dataList;
    }

    public Date getCreateDatetimeStart() {
        return createDatetimeStart;
    }

    public void setCreateDatetimeStart(Date createDatetimeStart) {
        this.createDatetimeStart = createDatetimeStart;
    }

    public Date getCreateDatetimeEnd() {
        return createDatetimeEnd;
    }

    public void setCreateDatetimeEnd(Date createDatetimeEnd) {
        this.createDatetimeEnd = createDatetimeEnd;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getUnreadSum() {
        return unreadSum;
    }

    public void setUnreadSum(Long unreadSum) {
        this.unreadSum = unreadSum;
    }

    public String getUser1Nickname() {
        return user1Nickname;
    }

    public void setUser1Nickname(String user1Nickname) {
        this.user1Nickname = user1Nickname;
    }

}
