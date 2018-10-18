/**
 * @Title IActionAO.java 
 * @Package com.ogc.standard.ao 
 * @Description 
 * @author taojian  
 * @date 2018年10月12日 下午2:42:20 
 * @version V1.0   
 */
package com.ogc.standard.ao;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Action;

/** 
 * @author: taojian 
 * @since: 2018年10月12日 下午2:42:20 
 * @history:
 */
public interface IActionAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String addAction(String type, String toType, String toCode,
            String creater);

    public Paginable<Action> queryActionPage(int start, int limit,
            Action condition);

    public Action getAction(String code);

    public void cancelAction(String creater, String toCode);
}
