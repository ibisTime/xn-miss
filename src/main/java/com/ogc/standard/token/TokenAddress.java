package com.ogc.standard.token;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ogc.standard.dao.base.ABaseDO;
import com.ogc.standard.domain.User;

/**
* 以太坊地址
* @author: haiqingzheng
* @since: 2017年10月23日 21:53:23
* @history:
*/
public class TokenAddress extends ABaseDO {

    private static final long serialVersionUID = 1L;

    // ID主键
    private String code;

    // 地址类型
    private String type;

    // 以太坊地址
    private String address;

    // 密码
    private String password;

    // 用户编号
    private String userId;

    // 账户编号
    private String accountNumber;

    // 初始金额
    private transient BigDecimal initialBalance;

    private String initialBalanceString;

    // 以太坊真实余额
    private transient BigDecimal balance;

    private String balanceString;

    // 状态
    private String status;

    // 创建时间
    private Date createDatetime;

    // 弃用时间
    private Date abandonDatetime;

    // 更新时间
    private Date updateDatetime;

    // keystore文件名
    private String keystoreName;

    // keystore文件内容
    private String keystoreContent;

    // 所属token币种
    private String symbol;

    // ****** DB propertiy end ******

    // 所属用户信息
    private User user;

    private Long useCount;

    private transient BigDecimal useAmount;

    private String useAmountString;

    // 状态列表
    private List<String> statusList;

    // 类型列表
    private List<String> typeList;

    // 余额起
    private BigDecimal balanceStart;

    // 余额止
    private BigDecimal balanceEnd;

    // 地址模糊查询
    private String addressForQuery;

    // 币种列表
    private List<String> currencyList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
        this.initialBalanceString = initialBalance.toString();
    }

    public String getInitialBalanceString() {
        return initialBalanceString;
    }

    public void setInitialBalanceString(String initialBalanceString) {
        this.initialBalanceString = initialBalanceString;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
        this.setBalanceString(balance.toString());
    }

    public String getBalanceString() {
        return balanceString;
    }

    public void setBalanceString(String balanceString) {
        this.balanceString = balanceString;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getAbandonDatetime() {
        return abandonDatetime;
    }

    public void setAbandonDatetime(Date abandonDatetime) {
        this.abandonDatetime = abandonDatetime;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getKeystoreName() {
        return keystoreName;
    }

    public void setKeystoreName(String keystoreName) {
        this.keystoreName = keystoreName;
    }

    public String getKeystoreContent() {
        return keystoreContent;
    }

    public void setKeystoreContent(String keystoreContent) {
        this.keystoreContent = keystoreContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUseCount() {
        return useCount;
    }

    public void setUseCount(Long useCount) {
        this.useCount = useCount;
    }

    public BigDecimal getUseAmount() {
        return useAmount;
    }

    public void setUseAmount(BigDecimal useAmount) {
        this.useAmount = useAmount;
        this.useAmountString = useAmount.toString();
    }

    public String getUseAmountString() {
        return useAmountString;
    }

    public void setUseAmountString(String useAmountString) {
        this.useAmountString = useAmountString;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    public BigDecimal getBalanceStart() {
        return balanceStart;
    }

    public void setBalanceStart(BigDecimal balanceStart) {
        this.balanceStart = balanceStart;
    }

    public BigDecimal getBalanceEnd() {
        return balanceEnd;
    }

    public void setBalanceEnd(BigDecimal balanceEnd) {
        this.balanceEnd = balanceEnd;
    }

    public String getAddressForQuery() {
        return addressForQuery;
    }

    public void setAddressForQuery(String addressForQuery) {
        this.addressForQuery = addressForQuery;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<String> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<String> currencyList) {
        this.currencyList = currencyList;
    }

}
