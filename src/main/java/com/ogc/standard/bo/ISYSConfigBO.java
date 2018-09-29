package com.ogc.standard.bo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.SYSConfig;

/** 
 * @author: haiqingzheng 
 * @since: 2016年4月17日 下午2:40:52 
 * @history:
 */
public interface ISYSConfigBO extends IPaginableBO<SYSConfig> {
    public int refreshSYSConfig(SYSConfig data);

    public SYSConfig getConfig(Long id);

    /**
     * 根据key获取value
     * @param ckey
     * @param companyCode
     * @param systemCode
     * @return 
     * @create: 2017年4月23日 下午8:08:12 xieyj
     * @history:
     */
    public SYSConfig getConfigValue(String ckey);

    public Map<String, String> getConfigsMap(String type);

    public Map<String, String> getSYSConfigMap(String type, String systemCode);

    public Double getDoubleValue(String key);

    public List<SYSConfig> queryConfigsList(String type);

    public BigDecimal getBigDecimalValue(String key);

}
