package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IArbitrateBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IArbitrateDAO;
import com.ogc.standard.domain.Arbitrate;
import com.ogc.standard.enums.EArbitrateStatus;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.exception.BizException;

@Component
public class ArbitrateBOImpl extends PaginableBOImpl<Arbitrate>
        implements IArbitrateBO {

    @Autowired
    private IArbitrateDAO arbitrateDAO;

    @Override
    public String submit(String tradeOrderCode, String yuangao, String beigao,
            String reason, String attach) {
        String code = OrderNoGenerater
            .generate(EGeneratePrefix.ARBITRATE.getCode());
        Arbitrate data = new Arbitrate();

        data.setCode(code);
        data.setTradeOrderCode(tradeOrderCode);
        data.setYuangao(yuangao);
        data.setBeigao(beigao);
        data.setReason(reason);
        data.setAttach(attach);
        data.setStatus(EArbitrateStatus.TO_HANDLE.getCode());
        data.setCreateDatetime(new Date());
        arbitrateDAO.insert(data);

        return code;
    }

    @Override
    public int handle(Arbitrate arbitrate, String result, String updater,
            String remark) {
        int count = 0;
        if (arbitrate != null) {
            arbitrate.setResult(result);
            arbitrate.setStatus(EArbitrateStatus.COMPLETE.getCode());
            arbitrate.setUpdater(updater);
            arbitrate.setUpdateDatetime(new Date());
            arbitrate.setRemark(remark);
            count = arbitrateDAO.updateHandle(arbitrate);
        }
        return count;
    }

    @Override
    public List<Arbitrate> queryArbitrateList(Arbitrate condition) {
        return arbitrateDAO.selectList(condition);
    }

    @Override
    public Arbitrate getArbitrate(String code) {
        Arbitrate data = null;
        if (StringUtils.isNotBlank(code)) {
            Arbitrate condition = new Arbitrate();
            condition.setCode(code);
            data = arbitrateDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.arb_NOTEXIST.getCode());
            }
        }
        return data;
    }

}
