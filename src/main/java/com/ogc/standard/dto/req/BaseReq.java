/**
 * @Title BaseReq.java 
 * @Package com.ogc.standard.dto.req 
 * @Description 
 * @author taojian  
 * @date 2018年9月20日 下午7:29:12 
 * @version V1.0   
 */
package com.ogc.standard.dto.req;

import java.io.Serializable;
import java.util.Locale;

/** 
 * @author: taojian 
 * @since: 2018年9月20日 下午7:29:12 
 * @history:
 */
public abstract class BaseReq implements Serializable {

    private static final long serialVersionUID = -1532302972775442373L;

    Locale defaultLocale = Locale.getDefault();

    // 语言
    private String language = defaultLocale.getLanguage();

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
