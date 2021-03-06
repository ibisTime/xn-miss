package com.ogc.standard.domain;

import java.util.Date;
import java.util.List;

import com.ogc.standard.dao.base.ABaseDO;

/**
* 选手
* @author: jiafr 
* @since: 2018-10-11 13:35:35
* @history:
*/
public class Player extends ABaseDO {

    private static final long serialVersionUID = -2579714681333747174L;

    // 编号
    private String code;

    // 赛区
    private String match;

    // 选手编号
    private String matchPlayCode;

    // 中文名
    private String cname;

    // 英文名
    private String ename;

    // 户籍
    private String nativePlace;

    // 身高
    private String height;

    // 体重
    private String weight;

    // 胸围
    private String xwei;

    // 腰围
    private String ywei;

    // 臀围
    private String twei;

    // 文字描述
    private String description;

    // 列表图
    private String listPic;

    // banner图
    private String bannerPics;

    // 详情图
    private String pics;

    // UI位置
    private String location;

    // UI次序
    private String orderNo;

    // 状态（0 草稿，1 已提交待审批，2 审批不通过，3 已审批待上架，4 已上架，5 已下架）
    private String status;

    // 创建时间
    private Date createDatetime;

    // 更新人
    private String updater;

    // 更新时间
    private Date updateDatetime;

    // 备注
    private String remark;

    // 加油总数
    private Long ticketSum;

    // 关注总数
    private Long attentionSum;

    // 分享总数
    private Long shareSum;

    // 足迹总数
    private Long scanSum;

    /************DB properties************/

    /************辅助字段************/

    // 中文名模糊查
    private String cnameQuery;

    // 英文名模糊查
    private String enameQuery;

    // 户籍模糊查
    private String nativePlaceQuery;

    // 创建时间起
    private Date createDatetimeStart;

    // 创建时间止
    private Date createDatetimeEnd;

    // 模糊查询字段
    private String fuzzyQuery;

    // 评论列表
    private List<Comment> commentList;

    // 查询选手详情的C端用户的userId
    private String userId;

    // 是否已关注
    private String isAttention;

    // 我为他加油次数
    private Long myTicketSum;

    // 评论总数
    private Long commentSum;

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getMatch() {
        return match;
    }

    public void setMatchPlayCode(String matchPlayCode) {
        this.matchPlayCode = matchPlayCode;
    }

    public String getMatchPlayCode() {
        return matchPlayCode;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCname() {
        return cname;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEname() {
        return ename;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeight() {
        return height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeight() {
        return weight;
    }

    public void setXwei(String xwei) {
        this.xwei = xwei;
    }

    public String getXwei() {
        return xwei;
    }

    public void setYwei(String ywei) {
        this.ywei = ywei;
    }

    public String getYwei() {
        return ywei;
    }

    public void setTwei(String twei) {
        this.twei = twei;
    }

    public String getTwei() {
        return twei;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setListPic(String listPic) {
        this.listPic = listPic;
    }

    public String getListPic() {
        return listPic;
    }

    public void setBannerPics(String bannerPics) {
        this.bannerPics = bannerPics;
    }

    public String getBannerPics() {
        return bannerPics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getPics() {
        return pics;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdater() {
        return updater;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setTicketSum(Long ticketSum) {
        this.ticketSum = ticketSum;
    }

    public Long getTicketSum() {
        return ticketSum;
    }

    public void setAttentionSum(Long attentionSum) {
        this.attentionSum = attentionSum;
    }

    public Long getAttentionSum() {
        return attentionSum;
    }

    public void setShareSum(Long shareSum) {
        this.shareSum = shareSum;
    }

    public Long getShareSum() {
        return shareSum;
    }

    public void setScanSum(Long scanSum) {
        this.scanSum = scanSum;
    }

    public Long getScanSum() {
        return scanSum;
    }

    public String getCnameQuery() {
        return cnameQuery;
    }

    public void setCnameQuery(String cnameQuery) {
        this.cnameQuery = cnameQuery;
    }

    public String getEnameQuery() {
        return enameQuery;
    }

    public void setEnameQuery(String enameQuery) {
        this.enameQuery = enameQuery;
    }

    public String getNativePlaceQuery() {
        return nativePlaceQuery;
    }

    public void setNativePlaceQuery(String nativePlaceQuery) {
        this.nativePlaceQuery = nativePlaceQuery;
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

    public String getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(String isAttention) {
        this.isAttention = isAttention;
    }

    public void setCreateDatetimeEnd(Date createDatetimeEnd) {
        this.createDatetimeEnd = createDatetimeEnd;
    }

    public String getFuzzyQuery() {
        return fuzzyQuery;
    }

    public void setFuzzyQuery(String fuzzyQuery) {
        this.fuzzyQuery = fuzzyQuery;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getMyTicketSum() {
        return myTicketSum;
    }

    public void setMyTicketSum(Long myTicketSum) {
        this.myTicketSum = myTicketSum;
    }

    public Long getCommentSum() {
        return commentSum;
    }

    public void setCommentSum(Long commentSum) {
        this.commentSum = commentSum;
    }

}
