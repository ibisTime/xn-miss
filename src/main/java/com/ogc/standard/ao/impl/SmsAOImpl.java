/**
 * @Title SmsAOImpl.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午3:14:45 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.ISmsAO;
import com.ogc.standard.bo.IReadBO;
import com.ogc.standard.bo.ISmsBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.domain.Read;
import com.ogc.standard.domain.Sms;
import com.ogc.standard.domain.User;
import com.ogc.standard.dto.req.XN805300Req;
import com.ogc.standard.dto.req.XN805301Req;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.enums.EReadStatus;
import com.ogc.standard.enums.ESmsStauts;
import com.ogc.standard.exception.BizException;

/** 
 * @author: dl 
 * @since: 2018年8月22日 下午3:14:45 
 * @history:
 */
@Service
public class SmsAOImpl implements ISmsAO {

    @Autowired
    private ISmsBO smsBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IReadBO readBO;

    @Override
    public String addSms(XN805300Req req) {
        Sms data = new Sms();
        data.setContent(req.getContent());
        data.setRemark(req.getRemark());
        data.setTitle(req.getTitle());
        data.setType(req.getType());
        data.setUpdater(req.getUpdater());
        data.setStatus(ESmsStauts.DRAFT.getCode());
        data.setCode(OrderNoGenerater.generate(EGeneratePrefix.XX.getCode()));
        return smsBO.saveSms(data);
    }

    @Override
    public void releaseSms(XN805301Req req) {

        Sms data = new Sms();
        data.setContent(req.getContent());
        data.setRemark(req.getRemark());
        data.setTitle(req.getTitle());
        data.setType(req.getType());
        data.setUpdater(req.getUpdater());
        data.setStatus(ESmsStauts.SENDED.getCode());
        // code是否为空
        if (StringUtils.isNotBlank(req.getCode())) {
            Sms sms = smsBO.getSms(req.getCode());
            // 判断该消息是否发布
            if (sms.getStatus().equals(ESmsStauts.SENDED.getCode())) {
                throw new BizException(EErrorCode_main.sms_PUBLISHED.getCode());
            }
            data.setCode(req.getCode());
            smsBO.refreshSms(data);
        } else {
            data.setCode(
                OrderNoGenerater.generate(EGeneratePrefix.XX.getCode()));
            smsBO.saveSms(data);
        }
        // read表中添加数据
        List<User> userList = userBO.queryUserList(null);
        List<Read> dataList = new ArrayList<Read>();
        Date date = new Date();
        for (User user : userList) {
            Read read = new Read();
            read.setSmsCode(data.getCode());
            read.setReceiveWay(data.getType());
            read.setUserId(user.getUserId());
            read.setStatus(EReadStatus.TOREAD.getCode());
            read.setCreateDatetime(date);
            dataList.add(read);
        }
        readBO.saveToRead(dataList);

    }

    @Override
    public void editStatus(String code, String updater, String remark) {
        smsBO.revokeSms(code, updater, remark);
        readBO.deleteRead(code);
    }

    @Override
    public Paginable<Sms> querySmsPage(int start, int limit, Sms condition) {
        return smsBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<Sms> querySmsList(Sms condition) {
        return smsBO.querySmsList(condition);
    }

    @Override
    public Sms getSms(String code) {
        if (!smsBO.isSmsExit(code)) {
            throw new BizException(EErrorCode_main.sms_NOTEXIST.getCode());
        }
        return smsBO.getSms(code);
    }

}
