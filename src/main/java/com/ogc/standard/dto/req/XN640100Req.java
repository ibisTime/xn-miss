/**
 * @Title XN640100Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author taojian  
 * @date 2018年10月10日 下午5:05:54 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/** 
 * 新增对话
 * @author: taojian 
 * @since: 2018年10月10日 下午5:05:54 
 * @history:
 */
public class XN640100Req {

    // 类型
    @NotBlank
    private String type;

    // 说话人1
    @NotBlank
    private String user1;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }
}
