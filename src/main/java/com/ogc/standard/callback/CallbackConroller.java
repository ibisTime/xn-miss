package com.ogc.standard.callback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ogc.standard.ao.ITicketAO;
import com.ogc.standard.ao.IWeChatAO;
import com.ogc.standard.dto.res.ChargeRes;
import com.ogc.standard.enums.EJourBizTypeUser;

/** 
 * @author: haiqingzheng 
 * @since: 2016年12月26日 下午1:44:16 
 * @history:
 */
@Controller
public class CallbackConroller {

    private static Logger logger = Logger.getLogger(CallbackConroller.class);

    @Autowired
    IWeChatAO weChatAO;

    @Autowired
    ITicketAO ticketAO;

    // 微信H5支付回调
    @RequestMapping("/wechat/H5/callback")
    public synchronized void doCallbackWechatH5(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            logger.info("******************微信公众号回调开始******************");

            // 获取回调参数
            PrintWriter out = response.getWriter();
            InputStream inStream = request.getInputStream();
            String result = getReqResult(out, inStream);
            logger.info("**** 公众号支付回调结果 ****：" + result);

            // 解析回调结果并通知业务biz
            ChargeRes chargeRes = weChatAO.doCallbackH5(result);

            // 业务特殊处理
            if (chargeRes.getIsSuccess()
                    && EJourBizTypeUser.TICKET.getCode().equals(
                        chargeRes.getBizType())) {
                ticketAO.upgradeRank(chargeRes.getOrderCode());
            }

            // 通知微信服务器(我已收到请求，不用再继续回调我了)
            String noticeStr = setXML("SUCCESS", "");
            out.print(new ByteArrayInputStream(noticeStr.getBytes(Charset
                .forName("UTF-8"))));
            logger.info("******************微信公众号回调结束******************");
        } catch (Exception e) {
            logger.info("******************公众号支付回调异常,原因：" + e.getMessage());
        }
    }

    private String getReqResult(PrintWriter out, InputStream inStream)
            throws IOException {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return new String(outSteam.toByteArray(), "utf-8");
    }

    public String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code
                + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }
}
