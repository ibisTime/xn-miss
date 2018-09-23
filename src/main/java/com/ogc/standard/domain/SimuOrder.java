package com.ogc.standard.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ogc.standard.dao.base.ABaseDO;

/**
 * 模拟交易委托单
 * @author: lei 
 * @since: 2018年8月22日 下午2:15:51 
 * @history:
 */
public class SimuOrder extends ABaseDO {

    private static final long serialVersionUID = 2292741290544803351L;

    // 编号
    private String code;

    // 用户编号
    private String userId;

    // 交易币种
    private String symbol;

    // 计价币种
    private String toSymbol;

    // 类型（市价/限价）
    private String type;

    // 买卖方向（买入/卖出）
    private String direction;

    // 委托价格(toSymbol)
    private BigDecimal price;

    // 委托总数量(symbol)
    private BigDecimal totalCount;

    // 委托金额(toSymbol)
    private BigDecimal totalAmount;

    // 已成交数量(symbol)
    private BigDecimal tradedCount;

    // 已成交总金额(toSymbol)
    private BigDecimal tradedAmount;

    // 已成交均价(toSymbol)
    private BigDecimal avgPrice;

    // 已成交总手续费
    private BigDecimal tradedFee;

    // 最后成交时间
    private Date lastTradedDatetime;

    // 创建时间
    private Date createDatetime;

    // 撤销时间
    private Date cancelDatetime;

    // 状态（已提交，部分成交，部分成交撤销，完全成交，已撤销）
    private String status;

    // 是否扫描
    private String isScan;

    // **** 查询条件 *****
    private List<String> statusList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getToSymbol() {
        return toSymbol;
    }

    public void setToSymbol(String toSymbol) {
        this.toSymbol = toSymbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigDecimal totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTradedCount() {
        return tradedCount;
    }

    public void setTradedCount(BigDecimal tradedCount) {
        this.tradedCount = tradedCount;
    }

    public BigDecimal getTradedAmount() {
        return tradedAmount;
    }

    public void setTradedAmount(BigDecimal tradedAmount) {
        this.tradedAmount = tradedAmount;
    }

    public BigDecimal getTradedFee() {
        return tradedFee;
    }

    public void setTradedFee(BigDecimal tradedFee) {
        this.tradedFee = tradedFee;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Date getLastTradedDatetime() {
        return lastTradedDatetime;
    }

    public void setLastTradedDatetime(Date lastTradedDatetime) {
        this.lastTradedDatetime = lastTradedDatetime;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getCancelDatetime() {
        return cancelDatetime;
    }

    public void setCancelDatetime(Date cancelDatetime) {
        this.cancelDatetime = cancelDatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsScan() {
        return isScan;
    }

    public void setIsScan(String isScan) {
        this.isScan = isScan;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

}
