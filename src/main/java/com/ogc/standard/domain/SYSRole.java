package com.ogc.standard.domain;

import java.util.Date;

import com.ogc.standard.dao.base.ABaseDO;

/**
 * 角色
 * @author: dl 
 * @since: 2018年8月18日 下午2:53:51 
 * @history:
 */
public class SYSRole extends ABaseDO {

    private static final long serialVersionUID = 5092189990777896572L;
    // ***********db properties***********

    // 角色编号
    private String code;

    // 角色名称
    private String name;

    // 更新人
    private String updater;

    // 更新时间
    private Date updateDatetime;

    // 备注
    private String remark;

    // 系统编号
    private String systemCode;

    // ***********db properties***********

    // 角色名称
    private String nameForQuery;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNameForQuery() {
        return nameForQuery;
    }

    public void setNameForQuery(String nameForQuery) {
        this.nameForQuery = nameForQuery;
    }

}
