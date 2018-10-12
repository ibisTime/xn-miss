package com.ogc.standard.dto.req;

import java.util.List;

/**
 * 我的订单分页查询
 * @author: jiafr 
 * @since: 2018年10月12日 下午12:40:55 
 * @history:
 */
public class XN640038Req extends APageReq {

    private static final long serialVersionUID = -9131787545964103736L;

    // 用户编号
    private String userId;

    // 状态（0 待支付，1 已支付，2 超时取消）
    private List<String> statusList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

}
