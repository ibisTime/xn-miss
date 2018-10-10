/**
 * @Title IReadDAO.java 
 * @Package com.ogc.standard.dao 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午7:26:44 
 * @version V1.0   
 */
package com.ogc.standard.dao;

import java.util.List;

import com.ogc.standard.dao.base.IBaseDAO;
import com.ogc.standard.domain.Read;

/** 
 * @author: dl 
 * @since: 2018年8月22日 下午7:26:44 
 * @history:
 */
public interface IReadDAO extends IBaseDAO<Read> {
    String NAMESPACE = IReadDAO.class.getName().concat(".");

    // 状态
    public int updateStatusRead(Read data);

    public void insert(List<Read> dataList);

    public void updateBatch(List<Read> dataList);

}
