/**
 * @Title XN640110Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author taojian  
 * @date 2018年10月11日 下午2:30:57 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/** 
 * 新增回复模版
 * @author: taojian 
 * @since: 2018年10月11日 下午2:30:57 
 * @history:
 */
public class XN640110Req {

    // 问题简介
    @NotBlank
    private String question;

    // 回复模版
    @NotBlank
    private String answer;

    // 更新人
    @NotBlank
    private String updater;

    // 备注
    private String remark;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

}
