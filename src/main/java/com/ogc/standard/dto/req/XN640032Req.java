package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 下单加油订单
 * @author: jiafr 
 * @since: 2018年10月12日 下午12:40:55 
 * @history:
 */
public class XN640032Req {

    // 订单编号
    @NotBlank
    private String code;

    // 支付方式
    @NotBlank
    private String payType;

    // 支付密码
    private String tradePwd;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

}
