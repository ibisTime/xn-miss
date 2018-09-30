/**
 * @Title UserEditApply.java 
 * @Package com.ogc.standard.domain 
 * @Description 
 * @author taojian  
 * @date 2018年9月13日 上午11:14:23 
 * @version V1.0   
 */
package com.ogc.standard.domain;

import java.util.Date;

import com.ogc.standard.dao.base.ABaseDO;

/** 
 * @author: taojian 
 * @since: 2018年9月13日 上午11:14:23 
 * @history:
 */
public class UserFieldApprove extends ABaseDO {

    private static final long serialVersionUID = 8499198152334876456L;

    // ***********db properties***********
    // ID主键
    private Long id;

    // 类型(1=手机,2=邮箱)
    private String type;

    // 修改字段
    private String field;

    // 验证码
    private String captcha;

    // 证件照手持面
    private String idHold;

    // 申请人
    private String applyUser;

    // 申请时间
    private Date applyDatetime;

    // 审核状态(0=待审核 1=审核通过 2=审核不通过)
    private String status;

    // 审核人
    private String approveUser;

    // 审核时间
    private Date approveDatetime;

    // 备注
    private String remark;

    // ***********db properties***********

    // 申请人信息
    private User applyUserInfo;

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getIdHold() {
        return idHold;
    }

    public void setIdHold(String idHold) {
        this.idHold = idHold;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public Date getApplyDatetime() {
        return applyDatetime;
    }

    public void setApplyDatetime(Date applyDatetime) {
        this.applyDatetime = applyDatetime;
    }

    public String getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser;
    }

    public Date getApproveDatetime() {
        return approveDatetime;
    }

    public void setApproveDatetime(Date approveDatetime) {
        this.approveDatetime = approveDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public User getApplyUserInfo() {
        return applyUserInfo;
    }

    public void setApplyUserInfo(User applyUserInfo) {
        this.applyUserInfo = applyUserInfo;
    }

}
