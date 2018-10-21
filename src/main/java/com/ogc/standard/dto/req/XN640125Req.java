/**
 * @Title XN640110Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author taojian  
 * @date 2018年10月11日 下午2:30:57 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

/** 
 * 分页查回复模版
 * @author: taojian 
 * @since: 2018年10月11日 下午2:30:57 
 * @history:
 */
public class XN640125Req extends APageReq {

    private static final long serialVersionUID = -8627980836155326153L;

    // 问题描述
    private String question;

    // 更新人
    private String updater;

    // 创建时间起
    private String createDatetimeStart;

    // 创建时间止
    private String createDatetimeEnd;

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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
