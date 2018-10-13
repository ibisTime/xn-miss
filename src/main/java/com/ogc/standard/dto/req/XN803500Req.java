package com.ogc.standard.dto.req;

import java.util.List;

/** 
 * @author: xieyj 
 * @since: 2016年12月23日 下午7:48:53 
 * @history:
 */
public class XN803500Req extends APageReq {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1L;

    // 户名
    private String realName;

    // 类别（B端账号，C端账号，平台账号）
    private String type;

    // 状态（正常/程序冻结/人工冻结）
    private String status;

    // 币种
    private String currency;

    // 币种列表
    private List<String> currencyList;

    // 最近一次变动对应的流水编号
    private String lastOrder;

    public List<String> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<String> currencyList) {
        this.currencyList = currencyList;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastOrder() {
        return lastOrder;
    }

    public void setLastOrder(String lastOrder) {
        this.lastOrder = lastOrder;
    }

}
