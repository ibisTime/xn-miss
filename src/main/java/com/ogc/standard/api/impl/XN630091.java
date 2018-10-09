package com.ogc.standard.api.impl;

import com.ogc.standard.api.AProcessor;
import com.ogc.standard.common.QnTokenImpl;
import com.ogc.standard.dto.res.XN805951Res;
import com.ogc.standard.enums.ESystemCode;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.ParaException;
import com.ogc.standard.spring.SpringContextHolder;

/**
 * 根据系统编号获取七牛uploadToken
 * @author: xieyj 
 * @since: 2016年10月11日 上午9:45:51 
 * @history:
 */
public class XN630091 extends AProcessor {
    private QnTokenImpl qnTokenImpl = SpringContextHolder
        .getBean(QnTokenImpl.class);

    @Override
    public Object doBusiness() throws BizException {
        return new XN805951Res(
            qnTokenImpl.getUploadToken(ESystemCode.MISS.getCode()));
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
    }
}
