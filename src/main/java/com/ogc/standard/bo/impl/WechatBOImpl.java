/**
 * @Title WechatBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年2月27日 下午3:16:19 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IWechatBO;
import com.ogc.standard.common.PropertiesUtil;
import com.ogc.standard.domain.CompanyChannel;
import com.ogc.standard.dto.res.XN002501Res;
import com.ogc.standard.enums.EWeChatType;
import com.ogc.standard.util.wechat.MD5;
import com.ogc.standard.util.wechat.MD5Util;
import com.ogc.standard.util.wechat.OrderUtil;
import com.ogc.standard.util.wechat.WXPrepay;

/** 
 * @author: haiqingzheng 
 * @since: 2017年2月27日 下午3:16:19 
 * @history:
 */
@Component
public class WechatBOImpl implements IWechatBO {

    @Override
    public String getPrepayIdH5(CompanyChannel companyChannel, String openId,
            String bizNote, String code, BigDecimal transAmount, String ip) {
        WXPrepay prePay = new WXPrepay();
        prePay.setAppid(companyChannel.getPrivateKey2());// 微信支付分配的公众账号ID
        prePay.setMch_id(companyChannel.getChannelCompany()); // 商户号
        prePay.setBody(companyChannel.getCompanyName() + "-" + bizNote); // 商品描述
        prePay.setOut_trade_no(code); // 订单号
        BigDecimal divide = transAmount.divide(BigDecimal.TEN);
        BigDecimal setScale = divide.setScale(0);
        prePay.setTotal_fee(setScale.toString()); // 订单总金额，厘转化成分

        prePay.setSpbill_create_ip(ip); // 用户IP
        prePay.setTrade_type(EWeChatType.JSAPI.getCode()); // 交易类型
        prePay.setNotify_url(PropertiesUtil.Config.WECHAT_H5_BACKURL);// 回调地址
        prePay.setPartnerKey(companyChannel.getPrivateKey1()); // 商户秘钥
        prePay.setOpenid(openId); // 支付者openid
        // prePay.setAttach(""); // 附加字段，回调时返回
        return prePay.submitXmlGetPrepayId();
    }

    @Override
    public XN002501Res getPayInfoH5(CompanyChannel companyChannel,
            String payCode, String prepayId) {
        SortedMap<String, String> nativeObj = new TreeMap<String, String>();
        nativeObj.put("appId", companyChannel.getPrivateKey2());
        nativeObj.put("timeStamp", OrderUtil.GetTimestamp());
        Random random = new Random();
        String randomStr = MD5.GetMD5String(String.valueOf(random
            .nextInt(10000)));
        nativeObj.put("nonceStr", MD5Util.MD5Encode(randomStr, "utf-8")
            .toLowerCase());
        nativeObj.put("package", "prepay_id=" + prepayId);
        nativeObj.put("signType", "MD5");
        nativeObj.put("paySign",
            createSign(nativeObj, companyChannel.getPrivateKey1()));

        XN002501Res res = new XN002501Res();
        res.setPrepayId(prepayId);
        res.setPayCode(payCode);
        res.setAppId(nativeObj.get("appId"));
        res.setTimeStamp(nativeObj.get("timeStamp"));
        res.setNonceStr(nativeObj.get("nonceStr"));
        res.setWechatPackage(nativeObj.get("package"));
        res.setSignType(nativeObj.get("signType"));
        res.setPaySign(nativeObj.get("paySign"));
        return res;
    }

    /**
     * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     */
    private String createSign(SortedMap<String, String> packageParams,
            String AppKey) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + AppKey);
        String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
        return sign;
    }
}
