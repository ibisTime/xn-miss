/**
 * @Title XN640005Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author taojian  
 * @date 2018年10月12日 上午11:12:35 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/** 
 * 评论选手
 * @author: taojian 
 * @since: 2018年10月12日 上午11:12:35 
 * @history:
 */
public class XN640005Req {
    // 评论人
    @NotBlank
    private String userId;

    // 选手编号
    @NotBlank
    private String code;

    // 评论内容
    @NotBlank
    private String content;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
