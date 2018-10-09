/**
 * @Title ISmsAO.java 
 * @Package com.ogc.standard.ao 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午2:51:40 
 * @version V1.0   
 */
package com.ogc.standard.ao;

import java.util.List;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Sms;
import com.ogc.standard.dto.req.XN805300Req;
import com.ogc.standard.dto.req.XN805301Req;

/** 
 * @author: dl 
 * @since: 2018年8月22日 下午2:51:40 
 * @history:
 */
public interface ISmsAO {

    // 保存消息（草稿）
    public String addSms(XN805300Req req);

    // 发布消息
    public void releaseSms(XN805301Req req);

    public Paginable<Sms> querySmsPage(int start, int limit, Sms condition);

    public List<Sms> querySmsList(Sms condition);

    public Sms getSms(String code);
}
