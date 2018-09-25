/**
 * @Title ReadBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午8:40:44 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IReadBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.IReadDAO;
import com.ogc.standard.domain.Read;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EReadStatus;
import com.ogc.standard.exception.BizException;

/** 
 * @author: dl 
 * @since: 2018年8月22日 下午8:40:44 
 * @history:
 */
@Component
public class ReadBOImpl extends PaginableBOImpl<Read> implements IReadBO {

    @Autowired
    private IReadDAO readDAO;

    @Override
    public void saveToRead(List<Read> dataList) {

        readDAO.insert(dataList);

    }

    @Override
    public void refreshStatusRead(long id) {
        if (!isReadExit(id)) {
            throw new BizException(EErrorCode_main.id_NOTEXIST.getCode());
        }
        Read data = new Read();
        data.setId(id);
        data.setStatus(EReadStatus.READ.getCode());
        data.setReadDatetime(new Date());
        readDAO.updateStatusRead(data);
    }

    @Override
    public void refreshStatusDelete(long id) {
        if (!isReadExit(id)) {
            throw new BizException(EErrorCode_main.id_NOTEXIST.getCode());
        }
        Read data = new Read();
        data.setId(id);
        data.setStatus(EReadStatus.DROPED.getCode());
        data.setDeleteDatetime(new Date());
        readDAO.updateStatusRead(data);
    }

    @Override
    public List<Read> queryReadList(Read condition) {
        return readDAO.selectList(condition);
    }

    @Override
    public Read getRead(long id) {
        Read condition = new Read();
        condition.setId(id);
        return readDAO.select(condition);
    }

    @Override
    public boolean isReadExit(long id) {
        Read read = new Read();
        read.setId(id);
        if (readDAO.selectTotalCount(read) >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteRead(String smsCode) {
        Read condition = new Read();
        condition.setSmsCode(smsCode);
        readDAO.delete(condition);
    }

}
