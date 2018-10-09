package com.ogc.standard.ao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.ISYSConfigAO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.SYSConfig;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

/**
 * 系统参数
 * @author: xieyj 
 * @since: 2016年7月23日 下午3:50:20 
 * @history:
 */
@Service
public class SYSConfigAOImpl implements ISYSConfigAO {

    @Autowired
    ISYSConfigBO sysConfigBO;

    @Override
    public int editSYSConfig(SYSConfig data) {
        int count = 0;
        if (data != null) {
            count = sysConfigBO.refreshSYSConfig(data);
        } else {
            throw new BizException(EErrorCode_main.id_NOTEXIST.getCode());
        }
        return count;
    }

    @Override
    public Paginable<SYSConfig> querySYSConfigPage(int start, int limit,
            SYSConfig condition) {
        return sysConfigBO.getPaginable(start, limit, condition);
    }

    @Override
    public Map<String, String> getConfigsMap(String type) {
        return sysConfigBO.getConfigsMap(type);
    }

    @Override
    public SYSConfig getSYSConfig(Long id) {
        return sysConfigBO.getConfig(id);
    }

    @Override
    public SYSConfig getSYSConfig(String key) {
        return sysConfigBO.getConfigValue(key);
    }

}
