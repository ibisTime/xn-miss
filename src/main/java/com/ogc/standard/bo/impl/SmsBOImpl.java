/**
 * @Title SmsBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午2:14:47 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ISmsBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.ISmsDAO;
import com.ogc.standard.domain.Sms;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.ESmsStauts;
import com.ogc.standard.exception.BizException;

/** 
 * @author: dl 
 * @since: 2018年8月22日 下午2:14:47 
 * @history:
 */
@Component
public class SmsBOImpl extends PaginableBOImpl<Sms> implements ISmsBO {

    @Autowired
    private ISmsDAO smsDAO;

    @Override
    public boolean isSmsExit(String code) {
        Sms sms = new Sms();
        sms.setCode(code);
        if (smsDAO.selectTotalCount(sms) >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public String saveSms(Sms data) {
        if (data != null) {

            data.setCreateDatetime(new Date());
            smsDAO.insert(data);
        }
        return data.getCode();
    }

    @Override
    public int refreshSms(Sms data) {
        int count = 0;
        if (data != null && StringUtils.isNotBlank(data.getCode())) {
            data.setUpdateDatetime(new Date());
            count = smsDAO.updateSms(data);
        }
        return count;
    }

    @Override
    public int revokeSms(String code, String updater, String remark) {
        Sms data = new Sms();
        data.setCode(code);
        data.setUpdater(updater);
        data.setRemark(remark);
        data.setUpdateDatetime(new Date());
        data.setStatus(ESmsStauts.WITHDRAW.getCode());
        return smsDAO.updateStatus(data);
    }

    @Override
    public List<Sms> querySmsList(Sms data) {
        return smsDAO.selectList(data);
    }

    @Override
    public Sms getSms(String code) {
        Sms data = null;
        if (StringUtils.isNotBlank(code)) {
            Sms condition = new Sms();
            condition.setCode(code);
            data = smsDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.sms_NOTEXIST.getCode());
            }
        }
        return data;
    }

}
