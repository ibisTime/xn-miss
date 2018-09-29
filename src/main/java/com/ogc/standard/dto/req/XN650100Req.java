package com.ogc.standard.dto.req;

/**
 * 
 * @author: lei 
 * @since: 2018年8月22日 下午4:15:42 
 * @history:
 */
public class XN650100Req extends APageReq {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 5780013307270124748L;

    // 交易所英文名
    private String exchange;

    // 基础币种
    private String symbol;

    // 计价币种
    private String tosymbol;

    // 法币类型
    private String referCurrency;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTosymbol() {
        return tosymbol;
    }

    public void setTosymbol(String tosymbol) {
        this.tosymbol = tosymbol;
    }

    public String getReferCurrency() {
        return referCurrency;
    }

    public void setReferCurrency(String referCurrency) {
        this.referCurrency = referCurrency;
    }

}
