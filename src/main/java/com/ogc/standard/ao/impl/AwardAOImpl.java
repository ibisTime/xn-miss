/**
 * @Title AwardAOImpl.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author taojian  
 * @date 2018年9月14日 下午7:21:20 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IAwardAO;
import com.ogc.standard.bo.IAwardBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Award;
import com.ogc.standard.enums.EAwardStatus;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;

/** 
 * @author: taojian 
 * @since: 2018年9月14日 下午7:21:20 
 * @history:
 */
@Service
public class AwardAOImpl implements IAwardAO {

    @Autowired
    private IAwardBO awardBO;

    @Autowired
    private IUserBO userBO;

    @Override
    public void settle(Long id, String isSettle, String remark) {

        Award data = awardBO.getAward(id);
        if (!EAwardStatus.TOHAND.getCode().equals(data.getStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "该奖励已处理");
        }
        awardBO.refreshStatus(data, isSettle, remark);
    }

    @Override
    public Paginable<Award> queryAwardPage(int start, int limit,
            Award condition) {
        Paginable<Award> page = awardBO.getPaginable(start, limit, condition);
        for (Award award : page.getList()) {
            award.setUser(userBO.getUser(award.getUserId()));
            award.setRelUser(userBO.getUser(award.getRefCode()));
        }
        return page;
    }

    @Override
    public List<Award> queryAwardList(Award condition) {
        List<Award> awardList = awardBO.queryAwardList(condition);
        return awardList;
    }

}
