/**
 * @Title XN628271Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author taojian  
 * @date 2018年10月12日 上午11:18:05 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

/** 
 * 分页查评论
 * @author: taojian 
 * @since: 2018年10月12日 上午11:18:05 
 * @history:
 */
public class XN628277Req extends APageReq {

    private static final long serialVersionUID = -8495274047403641559L;

    // 评论用户编号
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
