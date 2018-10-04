/**
 * @Title AwardMonthAOImpl.java 
 * @Package com.ogc.standard.ao.impl 
 * @Description 
 * @author taojian  
 * @date 2018年9月17日 下午3:10:18 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IAwardMonthAO;
import com.ogc.standard.bo.IAwardBO;
import com.ogc.standard.bo.IAwardMonthBO;
import com.ogc.standard.bo.IJourBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.AwardMonth;
import com.ogc.standard.domain.Jour;
import com.ogc.standard.dto.res.XN802397Res;
import com.ogc.standard.enums.ECoin;
import com.ogc.standard.enums.EJourBizTypeUser;

/** 
 * @author: taojian 
 * @since: 2018年9月17日 下午3:10:18 
 * @history:
 */
@Service
public class AwardMonthAOImpl implements IAwardMonthAO {

    @Autowired
    private IAwardMonthBO awardMonthBO;

    @Autowired
    private IAwardBO awardBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IJourBO jourBO;

    @Override
    public Paginable<AwardMonth> queryAwardMonthPage(int start, int limit,
            AwardMonth condition) {
        Paginable<AwardMonth> page = awardMonthBO.getPaginable(start, limit,
            condition);
        for (AwardMonth data : page.getList()) {
            data.setUser(userBO.getUser(data.getUserId()));
        }
        return page;
    }

    @Override
    public AwardMonth getAwardMonth(AwardMonth condition) {
        AwardMonth awardMonth = awardMonthBO.getAwardMonth(condition);

        if (awardMonth != null) {
            awardMonth.setUser(userBO.getUser(awardMonth.getUserId()));
        }

        return awardMonth;
    }

    @Override
    public XN802397Res statistics(String userId) {
        XN802397Res res = new XN802397Res();
        Jour condition = new Jour();
        condition.setUserId(userId);
        condition.setCurrency(ECoin.X.getCode());
        // cc交易
        condition.setBizType(EJourBizTypeUser.AJ_AWARD_CCORDER.getCode());
        BigDecimal ccCount = jourBO.getTotalAmount(condition);
        // bb交易
        condition.setBizType(EJourBizTypeUser.AJ_AWARD_BBORDER.getCode());
        BigDecimal bbCount = jourBO.getTotalAmount(condition);
        // 注册奖励
        condition.setBizType(EJourBizTypeUser.AJ_AWARD_REG.getCode());
        BigDecimal regCount = jourBO.getTotalAmount(condition);
        // 特殊奖励
        condition.setBizType(EJourBizTypeUser.AJ_AWARD_SPECIAL.getCode());
        BigDecimal specialCount = jourBO.getTotalAmount(condition);
        res.setBbTradeCount(bbCount);
        res.setCcTradeCount(ccCount);
        res.setRegRefCount(regCount);
        res.setPlatCount(specialCount);

        // Award condition = new Award();
        // XN802397Res res = new XN802397Res();
        // condition.setUserId(userId);
        // // cc交易
        // condition.setRefType(ERefType.CCTRADE.getCode());
        // List<Award> ccTradeList = awardBO.queryAwardList(condition);
        // BigDecimal ccCount = BigDecimal.ZERO;
        // for (Award award : ccTradeList) {
        // ccCount = ccCount.add(award.getCount());
        // }
        // res.setCcTradeCount(ccCount);
        // // bb交易
        // condition.setRefType(ERefType.BBTRADE.getCode());
        // List<Award> bbTradeList = awardBO.queryAwardList(condition);
        // BigDecimal bbCount = BigDecimal.ZERO;
        // for (Award award : bbTradeList) {
        // bbCount = bbCount.add(award.getCount());
        // }
        // res.setBbTradeCount(bbCount);
        // // 注册奖励
        // condition.setRefType(ERefType.REGIST.getCode());
        // List<Award> regTradeList = awardBO.queryAwardList(condition);
        // BigDecimal regCount = BigDecimal.ZERO;
        // for (Award award : regTradeList) {
        // regCount = regCount.add(award.getCount());
        // }
        // res.setRegRefCount(regCount);
        // // 特殊奖励
        // condition.setRefType(ERefType.SPECIAL.getCode());
        // List<Award> specialTradeList = awardBO.queryAwardList(condition);
        // BigDecimal specialCount = BigDecimal.ZERO;
        // for (Award award : specialTradeList) {
        // specialCount = specialCount.add(award.getCount());
        // }
        // res.setPlatCount(specialCount);

        AwardMonth conditionMonth = new AwardMonth();
        conditionMonth.setUserId(userId);
        List<AwardMonth> AwardMonthList = awardMonthBO
            .queryAwardMonthList(conditionMonth);
        // 已结算奖励，未结算奖励,不结算奖励
        BigDecimal settleCount = BigDecimal.ZERO;
        BigDecimal unsettleCount = BigDecimal.ZERO;
        BigDecimal nosettleCount = BigDecimal.ZERO;
        for (AwardMonth awardMonth : AwardMonthList) {
            settleCount = settleCount.add(awardMonth.getSettleCount());
            nosettleCount = nosettleCount.add(awardMonth.getNosettleCount());
            unsettleCount = unsettleCount.add(awardMonth.getUnsettleCount());
        }
        res.setSettleCount(settleCount);
        res.setNosettleCount(nosettleCount);
        res.setUnsettleCount(unsettleCount);
        return res;
    }

}
