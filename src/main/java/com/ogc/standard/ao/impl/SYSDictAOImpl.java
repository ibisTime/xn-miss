/**
 * @Title SYSDictAOImpl.java 
 * @Package com.xnjr.moom.ao.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年4月17日 下午5:19:00 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.ISYSDictAO;
import com.ogc.standard.bo.ISYSDictBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.SYSDict;
import com.ogc.standard.enums.EDictType;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

/** 
 * @author: haiqingzheng 
 * @since: 2016年4月17日 下午5:19:00 
 * @history:
 */
@Service
public class SYSDictAOImpl implements ISYSDictAO {
    @Autowired
    ISYSDictBO sysDictBO;

    @Override
    public Long addSYSDict(String type, String parentKey, String key,
            String value, String updater, String remark) {
        if (EDictType.SECOND.getCode().equals(type)) {
            if (StringUtils.isBlank(parentKey)) {
                throw new BizException(
                    EErrorCode_main.dict_SECONDPARENTKEY.getCode());
            }
            // 查看父节点是否存在
            SYSDict fDict = new SYSDict();
            fDict.setDkey(parentKey);
            fDict.setType(EDictType.FIRST.getCode());
            if (sysDictBO.getTotalCount(fDict) <= 0) {
                throw new BizException(
                    EErrorCode_main.dict_PARENTKEYNOTEXIST.getCode());
            }
            // 第二层数据字典 在当前父节点下key不能重复
            SYSDict condition = new SYSDict();
            condition.setDkey(key);
            condition.setParentKey(parentKey);
            condition.setType(EDictType.SECOND.getCode());
            if (sysDictBO.getTotalCount(condition) > 0) {
                throw new BizException(
                    EErrorCode_main.dict_KEYREPEAT.getCode());
            }
        } else if (EDictType.FIRST.getCode().equals(type)) {
            // 第一层数据字典 key不能重复
            SYSDict condition = new SYSDict();
            condition.setDkey(key);
            condition.setType(EDictType.FIRST.getCode());
            if (sysDictBO.getTotalCount(condition) > 0) {
                throw new BizException(
                    EErrorCode_main.dict_KEYREPEAT.getCode());
            }
        } else {
            throw new BizException("xn000000", "type类型不在枚举类中 0-第一层 1-第二层");
        }
        SYSDict sysDict = new SYSDict();
        sysDict.setType(type);
        if (EDictType.SECOND.getCode().equals(type)) {
            sysDict.setParentKey(parentKey);
        }
        sysDict.setDkey(key);
        sysDict.setDvalue(value);
        sysDict.setUpdater(updater);
        sysDict.setRemark(remark);
        return sysDictBO.saveSYSDict(sysDict);
    }

    @Override
    public int dropSYSDict(Long id) {
        int count = 0;
        if (id != null) {
            SYSDict condition = new SYSDict();
            condition.setId(id);
            if (sysDictBO.getTotalCount(condition) <= 0) {
                throw new BizException(EErrorCode_main.id_NOTEXIST.getCode());
            }
            count = sysDictBO.removeSYSDict(id);
        }
        return count;
    }

    @Override
    public int editSYSDict(Long id, String value, String updater,
            String remark) {
        SYSDict data = new SYSDict();
        data.setId(id);
        data.setDvalue(value);
        data.setUpdater(updater);
        data.setRemark(remark);
        return sysDictBO.refreshSYSDict(data);
    }

    @Override
    public Paginable<SYSDict> querySYSDictPage(int start, int limit,
            SYSDict condition) {
        return sysDictBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<SYSDict> querySysDictList(SYSDict condition) {
        return sysDictBO.querySYSDictList(condition);
    }

    @Override
    public SYSDict getSYSDict(Long id) {
        SYSDict sysDict = null;
        if (id != null) {
            SYSDict condition = new SYSDict();
            condition.setId(id);
            if (sysDictBO.getTotalCount(condition) <= 0) {
                throw new BizException(EErrorCode_main.id_NOTEXIST.getCode());
            }
            sysDict = sysDictBO.getSYSDict(id);
        }
        return sysDict;
    }
}
