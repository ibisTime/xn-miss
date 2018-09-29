/**
 * @Title XN625100Req.java 
 * @Package com.cdkj.coin.dto.req 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年11月9日 下午7:02:19 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/** 
 * @author: haiqingzheng 
 * @since: 2017年11月9日 下午7:02:19 
 * @history:
 */
public class XN802362Req {

    @NotBlank(message = "请先设置BTC归集阀值")
    private String balanceStart; // BTC归集阀值

    public String getBalanceStart() {
        return balanceStart;
    }

    public void setBalanceStart(String balanceStart) {
        this.balanceStart = balanceStart;
    }

}
