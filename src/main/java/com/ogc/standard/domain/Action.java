/**
 * @Title Action.java 
 * @Package com.ogc.standard.domain 
 * @Description 
 * @author taojian  
 * @date 2018年10月12日 下午1:47:57 
 * @version V1.0   
 */
package com.ogc.standard.domain;

import java.util.Date;

import com.ogc.standard.dao.base.ABaseDO;

/** 
 * 用户行为
 * @author: taojian 
 * @since: 2018年10月12日 下午1:47:57 
 * @history:
 */
public class Action extends ABaseDO {

    private static final long serialVersionUID = 1700595412438426024L;

    // **************************db properties **************************

    // 编号
    private String code;

    // 类型（1 分享，2 关注，3 足迹）
    private String type;

    // 对象类型
    private String toType;

    // 对象编号
    private String toCode;

    // 操作人
    private String creater;

    // 操作时间
    private Date createDatetime;

    // 备注
    private String remark;

    // **************************db properties **************************

    private Date createDatetimeStart;

    private Date createDatetimeEnd;

    private Player player;

    private User user;

    private Long myTicketSum;

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

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getMyTicketSum() {
        return myTicketSum;
    }

    public void setMyTicketSum(Long myTicketSum) {
        this.myTicketSum = myTicketSum;
    }

}
