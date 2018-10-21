package com.ogc.standard.exception;

import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.ELanguage;

public class ErrorMessage {

    public static void throwInterBizException(String errorCode,
            String language, Object... errorInfos) {
        if (ELanguage.main.getCode().equalsIgnoreCase(language)) {

            String errorInfo = String.format(
                EErrorCode_main.getMap().get(errorCode).getValue(), errorInfos);

            throw new BizException(errorCode, errorInfo);

        } else {
            throw new BizException("000000", "不支持的语言（unsupport language）");
        }
    }

}
