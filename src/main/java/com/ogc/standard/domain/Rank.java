package com.ogc.standard.domain;

import java.util.Date;

import com.ogc.standard.dao.base.ABaseDO;

/**
* 榜单
* @author: jiafr 
* @since: 2018-10-12 09:32:43
* @history:
*/
public class Rank extends ABaseDO {

    private static final long serialVersionUID = 1L;

    // 编号
    private String code;

    // 类型（1 日榜，2 总榜）
    private String type;

    // 批次
    private String batch;

    // 选手编号
    private String playerCode;

    // 排名
    private Integer rank;

    // 赛区
    private String match;

    // 创建时间
    private Date createDatetime;

    // 更新人
    private String updater;

    // 更新时间
    private Date updateDatetime;

    // 备注
    private String remark;

    // 实际加油总数
    private Integer ticketSum;

    // 虚拟加油总数
    private Integer fakeTicketSum;

    // 关注总数
    private Integer attentionSum;

    // 分享总数
    private Integer shareSum;

    // 足迹总数
    private Integer scanSum;

    /**********DB properties**********/

    /**********辅助字段**********/

    // 创建时间起
    private Date createDatetimeStart;

    // 创建时间止
    private Date createDatetimeEnd;

    // 选手中文名
    private String playerCname;

    // 选手英文名
    private String playerEname;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getBatch() {
        return batch;
    }

    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
    }

    public String getPlayerCode() {
        return playerCode;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getMatch() {
        return match;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdater() {
        return updater;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Integer getTicketSum() {
        return ticketSum;
    }

    public void setTicketSum(Integer ticketSum) {
        this.ticketSum = ticketSum;
    }

    public Integer getFakeTicketSum() {
        return fakeTicketSum;
    }

    public void setFakeTicketSum(Integer fakeTicketSum) {
        this.fakeTicketSum = fakeTicketSum;
    }

    public Integer getAttentionSum() {
        return attentionSum;
    }

    public void setAttentionSum(Integer attentionSum) {
        this.attentionSum = attentionSum;
    }

    public Integer getShareSum() {
        return shareSum;
    }

    public void setShareSum(Integer shareSum) {
        this.shareSum = shareSum;
    }

    public Integer getScanSum() {
        return scanSum;
    }

    public void setScanSum(Integer scanSum) {
        this.scanSum = scanSum;
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

    public String getPlayerCname() {
        return playerCname;
    }

    public void setPlayerCname(String playerCname) {
        this.playerCname = playerCname;
    }

    public String getPlayerEname() {
        return playerEname;
    }

    public void setPlayerEname(String playerEname) {
        this.playerEname = playerEname;
    }

}
