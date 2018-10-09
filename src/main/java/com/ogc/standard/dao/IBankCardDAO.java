package com.ogc.standard.dao;

import com.ogc.standard.dao.base.IBaseDAO;
import com.ogc.standard.domain.Bankcard;

/**
 * 
 * @author: lei 
 * @since: 2018年9月11日 下午5:41:48 
 * @history:
 */
public interface IBankCardDAO extends IBaseDAO<Bankcard> {
    String NAMESPACE = IBankCardDAO.class.getName().concat(".");

    public int update(Bankcard data);
}
