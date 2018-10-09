/**
 * @Title IWechatBO.java 
 * @Package com.ogc.standard.bo 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年2月27日 下午3:14:48 
 * @version V1.0   
 */
package com.ogc.standard.bo;

import java.math.BigDecimal;

import com.ogc.standard.domain.CompanyChannel;
import com.ogc.standard.dto.res.XN002500Res;
import com.ogc.standard.dto.res.XN002501Res;

/** 
 * @author: haiqingzheng 
 * @since: 2017年2月27日 下午3:14:48 
 * @history:
 */
public interface IWechatBO {
    public String getPrepayIdApp(CompanyChannel companyChannel, String bizNote,
            String code, BigDecimal transAmount, String ip, String backUrl);

    public XN002500Res getPayInfoApp(CompanyChannel companyChannel,
            String payCode, String prepayId);

    public String getPrepayIdH5(CompanyChannel companyChannel, String openId,
            String bizNote, String code, BigDecimal transAmount, String ip,
            String bizBackUrl);

    public XN002501Res getPayInfoH5(CompanyChannel companyChannel,
            String payCode, String prepayId);

    public String getPrepayIdNative(CompanyChannel companyChannel,
            String bizNote, String code, BigDecimal transAmount, String ip,
            String backUrl);

}
