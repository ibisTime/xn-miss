/**
 * @Title ReadAOImpl.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午9:15:00 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IReadAO;
import com.ogc.standard.bo.IEventBO;
import com.ogc.standard.bo.IReadBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Read;
import com.ogc.standard.enums.EReadStatus;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;

/** 
 * @author: dl 
 * @since: 2018年8月22日 下午9:15:00 
 * @history:
 */
@Service
public class ReadAOImpl implements IReadAO {

    @Autowired
    private IReadBO readBO;

    @Autowired
    private IEventBO eventBO;

    @Override
    public void readEvent(long id) {
        Read data = readBO.getRead(id);
        if (!EReadStatus.TOREAD.getCode().equals(data.getStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "当前消息不是待阅读");
        }
        readBO.refreshRead(data);
    }

    @Override
    public Paginable<Read> queryReadPage(int start, int limit, Read condition) {
        Paginable<Read> page = readBO.getPaginable(start, limit, condition);
        List<Read> readList = page.getList();
        for (Read read : readList) {
            read.setEventInfo(eventBO.getEvent(read.getToCode()));
        }
        return page;
    }

    @Override
    public Read getRead(long id) {
        Read read = readBO.getRead(id);
        read.setEventInfo(eventBO.getEvent(read.getToCode()));
        return read;
    }

}
