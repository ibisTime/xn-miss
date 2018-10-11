package com.ogc.standard.dto.req;

/**
 * 列表查询选手
 * @author: jiafr 
 * @since: 2018年10月11日 下午5:06:45 
 * @history:
 */
public class XN640017Req extends AListReq {

    private static final long serialVersionUID = 5050031921336009728L;

    // 赛区
    private String match;

    // 选手编号
    private String matchPlayCode;

    // 中文名
    private String cname;

    // 英文名
    private String ename;

    // 籍贯
    private String nativePlace;

    // 状态（0草稿1已提交待审批2已审批待上架3审批不通过4已上架5已下架）
    private String status;

    // 创建时间起
    private String createDatetimeStart;

    // 创建时间止
    private String createDatetimeEnd;

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getMatchPlayCode() {
        return matchPlayCode;
    }

    public void setMatchPlayCode(String matchPlayCode) {
        this.matchPlayCode = matchPlayCode;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
