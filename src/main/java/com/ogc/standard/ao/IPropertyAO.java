package com.ogc.standard.ao;

import com.ogc.standard.domain.Property;

/** 
 * 
 * @author: lei 
 * @since: 2018年9月26日 下午8:19:24 
 * @history:
 */
public interface IPropertyAO {

    public Property getProperty(String userId, String currency);

}
