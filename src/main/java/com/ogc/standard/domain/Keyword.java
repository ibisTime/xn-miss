package com.ogc.standard.domain;

import java.util.Date;

import com.ogc.standard.dao.base.ABaseDO;

/**
* 关键字
* @author: Silver
* @since: 2018年08月22日 上午10:28:38
* @history:
*/
public class Keyword extends ABaseDO {

    private static final long serialVersionUID = -4267033050985576742L;

    // 编号（自增长）
    private Integer id;

    // 词语
    private String word;

    // 作用等级
    private String level;

    // 反应(1 直接拦截/2 替换**/3 审核)
    private String reaction;

    // 更新人
    private String updater;

    // 更新时间
    private Date updateDatetime;

    // 备注
    private String remark;

    /**********DB properties******/

    private SYSUser sysUser;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getReaction() {
        return reaction;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public SYSUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SYSUser sysUser) {
        this.sysUser = sysUser;
    }

}
