/**
 * @Title XN805305Req.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午3:02:44 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

/** 
 * 列表查赛事信息
 * @author: dl 
 * @since: 2018年8月22日 下午3:02:44 
 * @history:
 */
public class XN805306Req {
    // 状态
    private String status;

    // 更新人
    private String updater;

    // 标题
    private String title;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
