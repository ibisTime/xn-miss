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
    // 编号
    private String code;

    // 类型
    private String type;

    // 说话人1
    private String user1;

    // 说话人2
    private String user2;

    // 创建时间
    private Date createDatetime;

    // 说话人1未读消息数
    private Long user1UnreadSum;

    // 说话人2未读消息数
    private Long user2UnreadSum;

    // ***********db properties***********

    // 昵称
    private String user1Nickname;

    private Date createDatetimeStart;

    private Date createDatetimeEnd;

    // 用户手机
    private String mobile;

    private List<Question> questionsList;

    public List<Question> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Question> questionsList) {
        this.questionsList = questionsList;
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

    public String getUser1Nickname() {
        return user1Nickname;
    }

    public void setUser1Nickname(String user1Nickname) {
        this.user1Nickname = user1Nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getUser1UnreadSum() {
        return user1UnreadSum;
    }

    public void setUser1UnreadSum(Long user1UnreadSum) {
        this.user1UnreadSum = user1UnreadSum;
    }

    public Long getUser2UnreadSum() {
        return user2UnreadSum;
    }

    public void setUser2UnreadSum(Long user2UnreadSum) {
        this.user2UnreadSum = user2UnreadSum;
    }

}
