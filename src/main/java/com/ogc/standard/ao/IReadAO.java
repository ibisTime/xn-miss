/**
 * @Title IReadAO.java 
 * @Package com.ogc.standard.ao 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午9:07:59 
 * @version V1.0   
 */
package com.ogc.standard.ao;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Read;

/** 
 * @author: dl 
 * @since: 2018年8月22日 下午9:07:59 
 * @history:
 */
public interface IReadAO {
    String DEFAULT_ORDER_COLUMN = "id";

    // 阅读消息
    public void readEvent(long id);

    // 分页查我的消息
    public Paginable<Read> queryReadPage(int start, int limit, Read condition);

    // 详情查询我的消息
    public Read getRead(long id);

}
