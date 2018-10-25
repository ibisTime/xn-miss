/**
 * @Title ReadDAOImpl.java 
 * @Package com.ogc.standard.dao.impl 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午7:30:29 
 * @version V1.0   
 */
package com.ogc.standard.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.IReadDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.Read;

/** 
 * @author: dl 
 * @since: 2018年8月22日 下午7:30:29 
 * @history:
 */
@Repository("readDAOImpl")
public class ReadDAOImpl extends AMybatisTemplate implements IReadDAO {

    @Override
    public int insert(Read data) {
        return 0;
    }

    @Override
    public int delete(Read data) {
        return 0;
    }

    @Override
    public Read select(Read condition) {
        return super.select(NAMESPACE.concat("select_read"), condition,
            Read.class);
    }

    @Override
    public long selectTotalCount(Read condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_read_count"),
            condition);
    }

    @Override
    public List<Read> selectList(Read condition) {
        return super.selectList(NAMESPACE.concat("select_read"), condition,
            Read.class);
    }

    @Override
    public int updateStatusRead(Read data) {
        return super.update(NAMESPACE.concat("update_status_read"), data);
    }

    @Override
    public List<Read> selectList(Read condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_read"), start, count,
            condition, Read.class);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void insert(List<Read> dataList) {
        super
            .insertBatch(NAMESPACE.concat("insert_read_list"), (List) dataList);
    }

}
