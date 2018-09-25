package com.ogc.standard.api.impl;

import com.ogc.standard.ao.IBtcMAddressAO;
import com.ogc.standard.api.AProcessor;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/** 
 * 生成散取地址
 * @author: taojian 
 * @since: 2018年9月11日 下午3:29:29 
 * @history:
 */
public class XN802570 extends AProcessor {

    private IBtcMAddressAO btcMAddressAO = SpringContextHolder
        .getBean(IBtcMAddressAO.class);

    @Override
    public Object doBusiness() throws BizException {
        return btcMAddressAO.addAddress();
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        // TODO Auto-generated method stub

    }

}
