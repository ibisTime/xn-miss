/**
 * @Title AwardBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author taojian  
 * @date 2018年9月14日 下午5:05:07 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IAwardBO;
import com.ogc.standard.bo.IAwardMonthBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.ITradeOrderBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.common.SysConstants;
import com.ogc.standard.dao.IAwardDAO;
import com.ogc.standard.domain.Award;
import com.ogc.standard.domain.TradeOrder;
import com.ogc.standard.domain.User;
import com.ogc.standard.enums.EAwardStatus;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.ECoin;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.ERefType;
import com.ogc.standard.enums.EUserKind;
import com.ogc.standard.exception.BizException;

/** 
 * @author: taojian 
 * @since: 2018年9月14日 下午5:05:07 
 * @history:
 */
@Component
public class AwardBOImpl extends PaginableBOImpl<Award> implements IAwardBO {

    @Autowired
    private IAwardDAO awardDAO;

    @Autowired
    private IAwardMonthBO awardMonthBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private ITradeOrderBO tradeOrderBO;

    @Override
    public Award getAward(Long id) {
        Award condition = new Award();
        condition.setId(id);
        Award award = awardDAO.select(condition);
        if (null == award) {
            throw new BizException(EErrorCode_main.awa_NOTEXIST.getCode());
        }
        return award;
    }

    @Override
    public void saveTradeAward(String refereeUserId, String userKind,
            String refCode, String refNote, BigDecimal tradeCount) {
        Award data = new Award();
        data.setUserId(refereeUserId);
        data.setUserKind(userKind);
        data.setRefCode(refCode);
        data.setRefNote(refNote);
        data.setCurrency(ECoin.X.getCode());
        data.setRefType(ERefType.BBTRADE.getCode());
        data.setCreateDatetime(new Date());
        data.setStatus(EAwardStatus.TOHAND.getCode());
        if (userKind.equals(EUserKind.QDS.getCode())) {
            BigDecimal rate = sysConfigBO
                .getBigDecimalValue(SysConstants.REFEREE_DUSER_FEE_RATE);
            data.setRate(rate);
            data.setCount(rate.multiply(tradeCount));
        } else {
            BigDecimal rate = sysConfigBO
                .getBigDecimalValue(SysConstants.REFEREE_CUSER_FEE_RATE);
            data.setRate(rate);
            data.setCount(rate.multiply(tradeCount));
        }
        data.setRemark("推荐用户币币交易分成");
        awardDAO.insert(data);
        // 记录
        if (awardMonthBO.isAwardMonthExist(refereeUserId)) {
            awardMonthBO.refreshAwardMonthUnsettle(data.getUserId(),
                data.getCount());
        } else {
            awardMonthBO.addAwardMonth(data, refNote);
        }
    }

    @Override
    public void saveOTCAward(String refereeUserId, String userKind,
            String refCode, String refNote, BigDecimal tradeCount) {
        Award data = new Award();
        data.setUserId(refereeUserId);
        data.setUserKind(userKind);
        data.setRefCode(refCode);
        data.setRefNote(refNote);
        data.setCurrency(ECoin.X.getCode());
        data.setRefType(ERefType.CCTRADE.getCode());
        data.setCreateDatetime(new Date());
        data.setStatus(EAwardStatus.TOHAND.getCode());
        if (userKind.equals(EUserKind.QDS.getCode())) {
            BigDecimal rate = sysConfigBO
                .getBigDecimalValue(SysConstants.REFEREE_DUSER_FEE_RATE);
            data.setRate(rate);
            data.setCount(rate.multiply(tradeCount));
        } else {
            BigDecimal rate = sysConfigBO
                .getBigDecimalValue(SysConstants.REFEREE_CUSER_FEE_RATE);
            data.setRate(rate);
            data.setCount(rate.multiply(tradeCount));
        }
        data.setRemark("推荐用户场外交易分成");
        awardDAO.insert(data);
        // 记录
        if (awardMonthBO.isAwardMonthExist(refereeUserId)) {
            awardMonthBO.refreshAwardMonthUnsettle(data.getUserId(),
                data.getCount());
        } else {
            awardMonthBO.addAwardMonth(data, refNote);
        }
    }

    @Override
    public void saveRegistAward(String userId, String userKind, String refCode,
            String refNote) {
        Award data = new Award();
        data.setUserId(userId);
        data.setUserKind(userKind);
        data.setRefCode(refCode);
        data.setRefNote(refNote);
        data.setCurrency(ECoin.X.getCode());
        data.setRefType(ERefType.REGIST.getCode());
        data.setCreateDatetime(new Date());
        data.setStatus(EAwardStatus.TOHAND.getCode());
        if (userKind.equals(EUserKind.QDS.getCode())) {
            data.setCount(
                sysConfigBO.getBigDecimalValue(SysConstants.DUSER_REG));
        } else {
            data.setCount(
                sysConfigBO.getBigDecimalValue(SysConstants.CUSER_REG));
        }
        data.setRemark("推荐注册分成");
        awardDAO.insert(data);
        // 记录
        if (awardMonthBO.isAwardMonthExist(userId)) {
            awardMonthBO.refreshAwardMonthUnsettle(data.getUserId(),
                data.getCount());
        } else {
            awardMonthBO.addAwardMonth(data, refNote);
        }
    }

    @Override
    public void refreshStatus(Award data, String isSettle, String remark) {
        if (isSettle.equals(EBoolean.YES.getCode())) {
            data.setStatus(EAwardStatus.HANDLE.getCode());
        } else {
            data.setStatus(EAwardStatus.NOHANDLE.getCode());
        }
        data.setHandleDatetime(new Date());
        data.setHandleNote(remark);
        awardDAO.updateStauts(data);
        // 记录
        if (awardMonthBO.isAwardMonthExist(data.getUserId())) {
            awardMonthBO.refreshAwardMonthUnsettle(data.getUserId(),
                data.getCount());
        } else {
            awardMonthBO.addAwardMonth(data, remark);
        }
    }

    @Override
    public List<Award> queryAwardList(Award condition) {
        return awardDAO.selectList(condition);
    }

    @Override
    public void saveSpecialAward(User user, BigDecimal count, String remark) {
        Award data = new Award();
        data.setUserId(user.getUserId());
        data.setUserKind(user.getKind());
        // data.setRefCode(refCode);
        // data.setRefNote(refNote);
        data.setCurrency(ECoin.X.getCode());
        data.setRefType(ERefType.SPECIAL.getCode());
        data.setCreateDatetime(new Date());
        data.setStatus(EAwardStatus.TOHAND.getCode());
        data.setCount(count);
        data.setRemark(remark);
        awardDAO.insert(data);
        // 记录
        if (awardMonthBO.isAwardMonthExist(user.getUserId())) {
            awardMonthBO.refreshAwardMonthUnsettle(data.getUserId(),
                data.getCount());
        } else {
            awardMonthBO.addAwardMonth(data, remark);
        }
    }

    @Override
    public BigDecimal count(Award condition) {
        List<Award> awardList = this.queryAwardList(condition);
        BigDecimal regCount = BigDecimal.ZERO;
        for (Award award : awardList) {
            regCount = regCount.add(award.getCount());
        }
        return regCount;
    }

    @Override
    public BigDecimal tradeCount(TradeOrder condition) {
        List<TradeOrder> orderList = tradeOrderBO
            .queryTradeOrderList(condition);
        BigDecimal awardCount = BigDecimal.ZERO;
        Award data = new Award();
        for (TradeOrder tradeOrder : orderList) {
            data.setRefCode(tradeOrder.getCode());

            awardCount = awardCount
                .add(getAwardByRefCode(tradeOrder.getCode()).getCount());
        }
        return awardCount;
    }

    @Override
    public Award getAwardByRefCode(String refCode) {
        Award condition = new Award();
        condition.setRefCode(refCode);
        return awardDAO.select(condition);
    }

}
