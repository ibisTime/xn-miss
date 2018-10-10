/**
 * @Title IReadBO.java 
 * @Package com.ogc.standard.bo 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午7:40:31 
 * @version V1.0   
 */
package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Read;

/** 
 * @author: dl 
 * @since: 2018年8月22日 下午7:40:31 
 * @history:
 */
public interface IReadBO extends IPaginableBO<Read> {
    // 我的信息是否存在
    public boolean isReadExit(long id);

    // 增加待阅读记录
    public void saveToRead(String toCode);

    // 批量删除消息记录
    public void refereshDelete(String toCode);

    // 阅读消息
    public void refreshStatus(long id, String status);

    // 分页查我的消息
    public List<Read> queryReadList(Read condition);

    public Read getRead(long id);
}
