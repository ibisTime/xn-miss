/**
 * @Title GoogleAuthBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年12月6日 下午4:55:49 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IGoogleAuthBO;
import com.ogc.standard.core.GoogleAuthenticator;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

/** 
 * @author: haiqingzheng 
 * @since: 2017年12月6日 下午4:55:49 
 * @history:
 */
@Component
public class GoogleAuthBOImpl implements IGoogleAuthBO {

    @Override
    public String generateSecretKey() {
        return GoogleAuthenticator.generateSecretKey();
    }

    @Override
    public void checkCode(String secret, String googleCaptcha, Long timeMsec) {
        GoogleAuthenticator ga = new GoogleAuthenticator();
        ga.setWindowSize(5); // should give 5 * 30 seconds of grace...
        boolean result = ga.check_code(secret,
            StringValidater.toLong(googleCaptcha), timeMsec);
        if (!result) {
            throw new BizException(EErrorCode_main.goog_FAILED.getCode());
        }
    }

}
