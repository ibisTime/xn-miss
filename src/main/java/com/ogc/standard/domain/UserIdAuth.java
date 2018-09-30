/**
 * @Title UserIdAuth.java 
 * @Package com.ogc.standard.domain 
 * @Description 
 * @author taojian  
 * @date 2018年9月13日 下午4:41:02 
 * @version V1.0   
 */
package com.ogc.standard.domain;

import java.util.Date;

import com.ogc.standard.dao.base.ABaseDO;

/** 
 * @author: taojian 
 * @since: 2018年9月13日 下午4:41:02 
 * @history:
 */
public class UserIdAuth extends ABaseDO {

    private static final long serialVersionUID = -7030907647985493370L;
    // ***********db properties***********

    // id
    private Long id;

    // 类型(1=手机,2=邮箱)
    private String type;

    // 隶属国家
    private String country;

    // 真实姓名
    private String realName;

    // 证件类型(1=身份证,2=护照，3=驾驶证)
    private String idKind;

    // 证件号码
    private String idNo;

    // 证件照正面
    private String idFace;

    // 证件照反面
    private String idOppo;

    // 手持证件照
    private String idHold;

    // 申请人
    private String applyUser;

    // 申请时间
    private Date applyDatetime;

    // 状态（0=待审核 1=审核通过 2=审核不通过)
    private String status;

    // 审核人
    private String approveUser;

    // 审核时间
    private Date approveDatetime;

    // 备注
    private String remark;

    // ***********db properties***********

    private User applyUserInfo;

    public Long getId() {
        return id;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdKind() {
        return idKind;
    }

    public void setIdKind(String idKind) {
        this.idKind = idKind;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdFace() {
        return idFace;
    }

    public void setIdFace(String idFace) {
        this.idFace = idFace;
    }

    public String getIdOppo() {
        return idOppo;
    }

    public void setIdOppo(String idOppo) {
        this.idOppo = idOppo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
