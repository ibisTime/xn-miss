package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.ISYSConfigDAO;
import com.ogc.standard.domain.SYSConfig;
import com.ogc.standard.dto.req.XN660918Req;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.http.BizConnecter;
import com.ogc.standard.http.JsonUtils;

/**
 * @author: xieyj 
 * @since: 2017年4月23日 下午6:19:30 
 * @history:
 */
@Component
public class SYSConfigBOImpl extends PaginableBOImpl<SYSConfig> implements
        ISYSConfigBO {

    static Logger logger = Logger.getLogger(SYSConfigBOImpl.class);

    @Autowired
    private ISYSConfigDAO sysConfigDAO;

    @Override
    public int refreshSYSConfig(SYSConfig data) {
        int count = 0;
        if (data != null) {
            data.setUpdateDatetime(new Date());
            count = sysConfigDAO.updateValue(data);
        }
        return count;
    }

    @Override
    public SYSConfig getConfig(Long id) {
        SYSConfig sysConfig = null;
        if (id != null) {
            SYSConfig condition = new SYSConfig();
            condition.setId(id);
            sysConfig = sysConfigDAO.select(condition);
        }
        return sysConfig;
    }

    @Override
    public SYSConfig getConfigValue(String ckey) {
        SYSConfig result = null;
        if (ckey != null) {
            SYSConfig condition = new SYSConfig();
            condition.setCkey(ckey);
            result = sysConfigDAO.select(condition);
            if (null == result) {
                throw new BizException(EErrorCode_main.id_NOTEXIST.getCode());
            }
        }
        return result;
    }

    @Override
    public Map<String, String> getConfigsMap(String type) {
        Map<String, String> map = new HashMap<String, String>();

        SYSConfig condition = new SYSConfig();
        condition.setType(type);
        List<SYSConfig> list = sysConfigDAO.selectList(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            for (SYSConfig sysConfig : list) {
                map.put(sysConfig.getCkey(), sysConfig.getCvalue());
            }
        }

        return map;
    }

    @Override
    public List<SYSConfig> queryConfigsList(String type) {
        SYSConfig condition = new SYSConfig();
        condition.setType(type);
        return sysConfigDAO.selectList(condition);
    }

    @Override
    public Double getDoubleValue(String key) {
        Double result = 0.0;
        SYSConfig config = getConfigValue(key);
        try {
            result = Double.valueOf(config.getCvalue());
        } catch (Exception e) {
            logger.error("参数名为" + key + "的配置转换成Double类型发生错误, 原因："
                    + e.getMessage());
        }
        return result;
    }

    @Override
    public Map<String, String> getSYSConfigMap(String type, String systemCode) {
        XN660918Req req = new XN660918Req();
        req.setType(type);
        req.setSystemCode(systemCode);
        String jsonStr = BizConnecter.getBizData("660918",
            JsonUtils.object2Json(req));
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    @Override
    public BigDecimal getBigDecimalValue(String key) {
        BigDecimal result = BigDecimal.ZERO;
        SYSConfig config = getConfigValue(key);
        try {
            result = new BigDecimal(config.getCvalue());
        } catch (Exception e) {
            logger.error("参数名为" + key + "的配置转换成BigDecimal类型发生错误, 原因："
                    + e.getMessage());
        }
        return result;
    }

    @Override
    public SYSConfig getSYSConfigNotException(String key, String companyCode,
            String systemCode) {
        SYSConfig sysConfig = null;
        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(systemCode)
                && StringUtils.isNotBlank(companyCode)) {
            SYSConfig condition = new SYSConfig();
            condition.setCkey(key);
            List<SYSConfig> sysConfigList = sysConfigDAO.selectList(condition);
            if (CollectionUtils.isNotEmpty(sysConfigList)) {
                sysConfig = sysConfigList.get(0);
            }
        }
        return sysConfig;
    }
}
