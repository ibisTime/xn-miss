package com.ogc.standard.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ogc.standard.api.IProcessor;
import com.ogc.standard.common.JsonUtil;
import com.ogc.standard.enums.EErrorCode_en_US;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.ELanguage;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.http.BizConnecter;
import com.ogc.standard.http.JsonUtils;

public class DispatcherImpl implements IDispatcher {
    protected static final Logger logger = LoggerFactory
        .getLogger(DispatcherImpl.class);

    @Override
    public String doDispatcher(String transcode, String inputParams,
            String operator, String language) {
        // ConfigDescribe configDescribe = ConfigLoader.loadConfig();
        /*
         * if (StringUtils.isNotBlank(transcode) && configDescribe != null) {
         * List<String> codeList = configDescribe.getCodeList(); if
         * (codeList.contains(transcode)) { classname =
         * "com.ogc.standard.api.impl.XN" + transcode; } }
         */
        String result = null;
        ReturnMessage rm = new ReturnMessage();
        try {
            // 加载配置文件,proxy实例化
            // 验证功能号是否直接转发
            Object data = null;
            if (!isDeal(transcode)) {// 不需要处理直接转发
                String resultData = BizConnecter.getBizData(transcode,
                    inputParams);
                data = JsonUtils.json2Bean(resultData, Object.class);
            } else {
                String classname = "com.ogc.standard.api.impl.XNOther";
                classname = "com.ogc.standard.api.impl.XN" + transcode;
                IProcessor processor = (IProcessor) ReflectUtil
                    .getInstance(classname);
                // 接口调用
                data = processor.doProcessor(inputParams, operator);
            }
            rm.setErrorCode(EErrorCode.SUCCESS.getCode());
            rm.setErrorInfo(EErrorCode.SUCCESS.getValue());
            if (data == null) {
                data = new Object();
            }
            rm.setData(data);
        } catch (Exception e) {

            System.out.println("Exception Post-o:" + e.getMessage());

            if (e instanceof BizException) {

                String errorCode = ((BizException) e).getErrorCode();

                String errorInfo = "";
                if (ELanguage.en_US.getCode().equalsIgnoreCase(language)) {

                    EErrorCode_en_US eErrorCode_en_US = EErrorCode_en_US
                        .getMap().get(errorCode);

                    if (null == eErrorCode_en_US) {
                        errorInfo = ((BizException) e).getErrorMessage();
                    } else {
                        errorInfo = String.format(eErrorCode_en_US.getValue(),
                            ((BizException) e).getErrorParams());
                    }

                } else {

                    EErrorCode_main eErrorCode_main = EErrorCode_main.getMap()
                        .get(errorCode);

                    if (null == eErrorCode_main) {
                        errorInfo = ((BizException) e).getErrorMessage();
                    } else {
                        errorInfo = String.format(eErrorCode_main.getValue(),
                            ((BizException) e).getErrorParams());
                    }

                }

                rm.setErrorCode(errorCode);
                rm.setErrorInfo(errorInfo);
                rm.setData("");

            } else if (e instanceof ParaException) {
                rm.setErrorCode(EErrorCode.PARA_ERR.getCode());
                rm.setErrorInfo(((ParaException) e).getErrorMessage());
                rm.setData("");
            } else if (e instanceof NullPointerException) {
                rm.setErrorCode(EErrorCode.OTHER_ERR.getCode());
                rm.setErrorInfo(((NullPointerException) e).getMessage());
                rm.setData("");
            } else {
                rm.setErrorCode(EErrorCode.OTHER_ERR.getCode());
                rm.setErrorInfo("系统错误，请联系客服");
                rm.setData("");
            }
        } finally {
            result = JsonUtil.Object2Json(rm);
        }
        return result;
    }

    public boolean isDeal(String transcode) {
        return true;
        // boolean result = false;// 结果 false 不需要处理直接转发，true 需要处理再转发
        // if (StringUtils.isNotBlank(transcode) && transcode.startsWith("802"))
        // {
        // try {
        // String filtercode = PropertiesUtil.Config.FILTER_CODE;
        // if (StringUtils.isNotBlank(filtercode)
        // && filtercode.contains(transcode)) {
        // result = true;
        // }
        // } catch (Exception e) {
        // logger.error("转发功能号错误:" + e.getMessage());
        // }
        // }
        // return result;
    }
}
