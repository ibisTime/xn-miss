/**
 * @Title XN616000Req.java 
 * @Package com.cdkj.coin.dto.req 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年11月7日 下午2:03:47 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

/** 
 * @author: haiqingzheng 
 * @since: 2017年11月7日 下午2:03:47 
 * @history:
 */
public class XN616000Req extends BaseReq {

    private static final long serialVersionUID = -3365683656458383039L;

    private String address;

    private String type;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
