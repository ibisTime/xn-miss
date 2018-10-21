/**
 * @Title ReadBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午8:40:44 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IReadBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.IReadDAO;
import com.ogc.standard.domain.Read;
import com.ogc.standard.domain.User;
import com.ogc.standard.enums.EReadStatus;
import com.ogc.standard.enums.ESmsType;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;

/** 
 * @author: dl 
 * @since: 2018年8月22日 下午8:40:44 
 * @history:
 */
@Component
public class ReadBOImpl extends PaginableBOImpl<Read> implements IReadBO {

    @Autowired
    private IReadDAO readDAO;

    @Autowired
    private IUserBO userBO;

    @Override
    public void saveToRead(String toCode) {
        User condition = null;
        List<User> userList = userBO.queryUserList(condition);
        List<Read> readList = new ArrayList<Read>();
        Date now = new Date();
        if (userList.isEmpty()) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "无用户可阅读");
        }
        for (User user : userList) {
            Read read = new Read();
            read.setUserId(user.getUserId());
            read.setToType(ESmsType.EVENT.getCode());
            read.setToCode(toCode);
            read.setStatus(EReadStatus.TOREAD.getCode());
            read.setCreateDatetime(now);
            readList.add(read);
        }
        readDAO.insert(readList);

    }

    @Override
    public void refreshRead(Read data) {
        data.setStatus(EReadStatus.READ.getCode());
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
    public void refereshDelete(String toCode) {
        if (StringUtils.isNotBlank(toCode)) {
            Read condition = new Read();
            condition.setToCode(toCode);
            List<Read> dataList = queryReadList(condition);
            if (CollectionUtils.isNotEmpty(dataList)) {
                for (Read read : dataList) {
                    read.setStatus(EReadStatus.DROPED.getCode());
                }
                readDAO.updateBatch(dataList);
            }
        }
    }

}
