package com.ogc.standard.dto.req;


/**
 * 列表查询榜单
 * @author: jiafr 
 * @since: 2018年10月12日 上午11:30:31 
 * @history:
 */
public class XN640027Req extends AListReq {

    private static final long serialVersionUID = 3944836023197503768L;

    // 类型（1总榜2日榜）
    private String type;

    // 批次
    private String batch;

    // 选手编号
    private String playerCode;

    // 排名
    private String rank;

    // 赛区
    private String match;

    // 创建时间起
    private String createDatetimeStart;

    // 创建时间止
    private String createDatetimeEnd;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getCreateDatetimeStart() {
        return createDatetimeStart;
    }

    public void setCreateDatetimeStart(String createDatetimeStart) {
        this.createDatetimeStart = createDatetimeStart;
    }

    public String getCreateDatetimeEnd() {
        return createDatetimeEnd;
    }

    public void setCreateDatetimeEnd(String createDatetimeEnd) {
        this.createDatetimeEnd = createDatetimeEnd;
    }
}
