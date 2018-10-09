package com.ogc.standard.ao;

import java.util.Map;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.SYSConfig;

/**
 * @author: xieyj 
 * @since: 2016年9月17日 下午4:02:23 
 * @history:
 */
public interface ISYSConfigAO {

    String DEFAULT_ORDER_COLUMN = "id";

    public int editSYSConfig(SYSConfig data);

    public Paginable<SYSConfig> querySYSConfigPage(int start, int limit,
            SYSConfig condition);

    public SYSConfig getSYSConfig(Long id);

    public SYSConfig getSYSConfig(String key);

    public Map<String, String> getConfigsMap(String type);

}
