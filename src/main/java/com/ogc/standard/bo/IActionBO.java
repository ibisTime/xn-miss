/**
 * @Title IActionBO.java 
 * @Package com.ogc.standard.bo 
 * @Description 
 * @author taojian  
 * @date 2018年10月12日 下午2:22:33 
 * @version V1.0   
 */
package com.ogc.standard.bo;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Action;

/** 
 * @author: taojian 
 * @since: 2018年10月12日 下午2:22:33 
 * @history:
 */
public interface IActionBO extends IPaginableBO<Action> {

    public boolean isActionExist(String userId, String toCode, String actionType);

    public String saveAction(String type, String toType, String toCode,
            String creater);

    public Action getAction(String code);

    public Action getActionByTypeToCodeCreater(String type, String toCode,
            String creater);

    public void removeAction(String code);

}
