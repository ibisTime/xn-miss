/**
 * @Title UserExtAOImpl.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 上午12:37:00 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IUserExtAO;
import com.ogc.standard.bo.ISmsOutBO;
import com.ogc.standard.bo.IUserExtBO;
import com.ogc.standard.domain.UserExt;
import com.ogc.standard.dto.req.XN805085Req;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

/** 
 * @author: dl 
 * @since: 2018年8月22日 上午12:37:00 
 * @history:
 */
@Service
public class UserExtAOImpl implements IUserExtAO {
    @Autowired
    private IUserExtBO userExtBO;

    @Autowired
    private ISmsOutBO smsOutBO;

    @Override
    public String editUserExt(XN805085Req req) {
        UserExt data = new UserExt();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        data.setUserId(req.getUserId());
        data.setBirthday(req.getBirthday());
        data.setDiploma(req.getDiploma());
        data.setEmail(req.getEmail());
        data.setGender(req.getGender());
        try {
            data.setGradDatetime(format.parse(req.getGradDatetime()));
        } catch (ParseException e) {
            throw new BizException(EErrorCode_main.date_WRONGFORMAT.getCode());
        }
        data.setIntroduce(req.getIntroduce());
        data.setOccupation(req.getOccupation());
        data.setPdf(req.getPdf());
        userExtBO.refreshUserExt(data);
        return req.getUserId();
    }

}
