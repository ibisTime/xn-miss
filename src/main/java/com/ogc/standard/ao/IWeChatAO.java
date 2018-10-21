/**
 * @Title IWeChatAO.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月23日 上午11:23:39 
 * @version V1.0   
 */
package com.ogc.standard.ao;

import java.math.BigDecimal;

import com.ogc.standard.domain.CallbackResult;
import com.ogc.standard.dto.res.ChargeRes;
import com.ogc.standard.dto.res.XN002501Res;

/** 
 * @author: haiqingzheng 
 * @since: 2016年12月23日 上午11:23:39 
 * @history:
 */
public interface IWeChatAO {

    public XN002501Res getPrepayIdH5(String applyUser, String openId,
            String toUser, String payGroup, String refNo, String bizType,
            String bizNote, BigDecimal transAmount);

    public ChargeRes doCallbackH5(String result);

    public String getAccessToken(String appId, String appSecret);

    public void doBizCallback(CallbackResult callbackResult);

}
