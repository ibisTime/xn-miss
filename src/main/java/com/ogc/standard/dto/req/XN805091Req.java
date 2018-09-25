/**
 * @Title XN805091Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author taojian  
 * @date 2018年9月14日 下午3:04:56 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/** 
 * @author: taojian 
 * @since: 2018年9月14日 下午3:04:56 
 * @history:
 */
public class XN805091Req extends BaseReq {

    private static final long serialVersionUID = 4115519523625742480L;

    // 用户编号
    @NotBlank
    private String userId;

    // 负责区域
    @NotBlank
    private String respArea;

    @NotBlank
    private String updater;

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRespArea() {
        return respArea;
    }

    public void setRespArea(String respArea) {
        this.respArea = respArea;
    }
}
