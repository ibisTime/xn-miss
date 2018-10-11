package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 修改选手
 * @author: jiafr 
 * @since: 2018年10月11日 下午2:30:10 
 * @history:
 */
public class XN640002Req {

    // 编号
    @NotBlank
    private String code;

    // 赛区
    @NotBlank
    private String match;

    // 选手编号
    @NotBlank
    private String matchPlayCode;

    // 中文名
    @NotBlank
    private String cname;

    // 英文名
    @NotBlank
    private String ename;

    // 户籍
    @NotBlank
    private String nativePlace;

    // 身高
    @NotBlank
    private String height;

    // 体重
    @NotBlank
    private String weight;

    // 胸围
    @NotBlank
    private String xwei;

    // 腰围
    @NotBlank
    private String ywei;

    // 臀围
    @NotBlank
    private String twei;

    // 文字描述
    @NotBlank
    private String description;

    // 列表图
    @NotBlank
    private String listPic;

    // banner图
    @NotBlank
    private String bannerPics;

    // 详情图
    @NotBlank
    private String pics;

    // 更新人
    @NotBlank
    private String updater;

    // 备注
    private String remark;

    // 操作类型（0保存1发布）
    @NotBlank
    private String bizType;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getXwei() {
        return xwei;
    }

    public void setXwei(String xwei) {
        this.xwei = xwei;
    }

    public String getYwei() {
        return ywei;
    }

    public void setYwei(String ywei) {
        this.ywei = ywei;
    }

    public String getTwei() {
        return twei;
    }

    public void setTwei(String twei) {
        this.twei = twei;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getListPic() {
        return listPic;
    }

    public void setListPic(String listPic) {
        this.listPic = listPic;
    }

    public String getBannerPics() {
        return bannerPics;
    }

    public void setBannerPics(String bannerPics) {
        this.bannerPics = bannerPics;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

}
