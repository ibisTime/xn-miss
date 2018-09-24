package com.ogc.standard.ao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IGoogleAuthAO;
import com.ogc.standard.bo.IGoogleAuthBO;

/** 
 * 
 * @author: lei 
 * @since: 2018年9月21日 下午8:16:03 
 * @history:
 */
@Service
public class GoogleAuthAOImpl implements IGoogleAuthAO {

    @Autowired
    private IGoogleAuthBO googleAuthBO;

    @Override
    public String generateSecretKey() {
        return googleAuthBO.generateSecretKey();
    }

}
