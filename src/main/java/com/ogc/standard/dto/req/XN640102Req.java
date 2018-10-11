/**
 * @Title XN640101Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午9:56:32 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 发送消息 
 * @author: taojian 
 * @since: 2018年10月10日 下午9:56:32 
 * @history:
 */
public class XN640102Req {

    // 会话编号
    @NotBlank
    private String code;

    // 说话人1
    @NotBlank
    private String user1;

    // 回复内容
    @NotBlank
    private String content;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
