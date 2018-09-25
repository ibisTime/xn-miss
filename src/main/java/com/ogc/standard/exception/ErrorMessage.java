package com.ogc.standard.exception;

import com.ogc.standard.enums.ELanguage;
import com.ogc.standard.enums.EErrorCode_en_US;
import com.ogc.standard.enums.EErrorCode_main;

public class ErrorMessage {

    public static void throwInterBizException(String errorCode, String language,
            Object... errorInfos) {
        if (ELanguage.main.getCode().equalsIgnoreCase(language)) {

            String errorInfo = String.format(
                EErrorCode_main.getMap().get(errorCode).getValue(), errorInfos);

            throw new BizException(errorCode, errorInfo);

        } else if (ELanguage.en_US.getCode().equalsIgnoreCase(language)) {

            String errorInfo = String.format(
                EErrorCode_en_US.getMap().get(errorCode).getValue(), errorInfos);

            throw new BizException(errorCode, errorInfo);

        } else {
            throw new BizException("000000", "不支持的语言（unsupport language）");
        }
    }

}
