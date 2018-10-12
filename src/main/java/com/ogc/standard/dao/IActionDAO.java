/**
 * @Title IActionDAO.java 
 * @Package com.ogc.standard.dao 
 * @Description 
 * @author taojian  
 * @date 2018年10月12日 下午2:03:38 
 * @version V1.0   
 */
package com.ogc.standard.dao;

import com.ogc.standard.dao.base.IBaseDAO;
import com.ogc.standard.domain.Action;

/** 
 * @author: taojian 
 * @since: 2018年10月12日 下午2:03:38 
 * @history:
 */
public interface IActionDAO extends IBaseDAO<Action> {

    String NAMESPACE = IActionDAO.class.getName().concat(".");
}
