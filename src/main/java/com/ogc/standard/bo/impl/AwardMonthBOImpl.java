/**
 * @Title AwardMonthBOImpl.java 
 * @Package com.ogc.standard.bo.impl 
 * @Description 
 * @author taojian  
 * @date 2018年9月17日 下午2:58:40 
 * @version V1.0   
 */
package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IAwardMonthBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.dao.IAwardMonthDAO;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Award;
import com.ogc.standard.domain.AwardMonth;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

/** 
 * @author: taojian 
 * @since: 2018年9月17日 下午2:58:40 
 * @history:
 */
@Component
public class AwardMonthBOImpl extends PaginableBOImpl<AwardMonth>
        implements IAwardMonthBO {
    @Autowired
    private IAwardMonthDAO awardMonthDAO;

    @Autowired
    private IAccountBO accountBO;

    @Override
    public boolean isAwardMonthExist(String userId) {
        AwardMonth condition = new AwardMonth();
        condition.setUserId(userId);
        condition.setNow(new Date());
        if (this.getTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public AwardMonth getAwardMonth(Long id) {
        AwardMonth condition = new AwardMonth();
        condition.setId(id);
        return awardMonthDAO.select(condition);
    }

    @Override
    public AwardMonth getAwardMonth(AwardMonth condition) {

        return awardMonthDAO.select(condition);
    }

    @Override
    public int addAwardMonth(Award award, String remark) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        Date date = calendar.getTime();
        AwardMonth condition = new AwardMonth();
        condition.setUserId(award.getUserId());
        condition.setNow(date);
        AwardMonth awardMonth = awardMonthDAO.select(condition);
        int count = 0;
        if (award != null) {

            AwardMonth data = new AwardMonth();
            data.setUserId(award.getUserId());
            if (awardMonth != null) {
                data.setUnsettleCount(awardMonth.getNextUnsettleCount());
            } else {
                data.setUnsettleCount(BigDecimal.ZERO);
            }
            data.setSettleCount(BigDecimal.ZERO);
            data.setNosettleCount(BigDecimal.ZERO);
            data.setNextUnsettleCount(award.getCount());
            data.setStartDate(DateUtil.getCurrentMonthFirstDay());
            data.setEndDate(DateUtil.getCurrentMonthLastDay());
            data.setCreateDatetime(now);
            data.setRemark(remark);
            count = awardMonthDAO.insert(data);
        }
        return count;
    }

    @Override
    public void refreshAwardMonthSettle(Award data, String handleResult) {
        Date createDate = data.getCreateDatetime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createDate); // 设置为创建奖励时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); // 设置为下一个月
        Date date = calendar.getTime();
        AwardMonth condition = new AwardMonth();
        condition.setUserId(data.getUserId());
        condition.setNow(date);
        AwardMonth awardMonth = awardMonthDAO.select(condition);
        if (awardMonth == null) {
            throw new BizException(EErrorCode_main.awa_MONTHSETTLE.getCode());
        }
        awardMonth.setUnsettleCount(
            awardMonth.getUnsettleCount().subtract(data.getCount()));
        Account dbAccount = accountBO.getAccountByUser(data.getUserId(),
            data.getCurrency());
        awardMonth.setCurrentCount(dbAccount.getAmount());
        awardMonth.setSettleDatetime(new Date());
        if (EBoolean.YES.getCode().equals(handleResult)) {
            awardMonth.setSettleCount(
                awardMonth.getSettleCount().add(data.getCount()));
        } else {
            awardMonth.setNosettleCount(
                awardMonth.getNosettleCount().add(data.getCount()));
        }
        awardMonth.setRemark("结算奖励");
        awardMonthDAO.updateSettle(awardMonth);
    }

    @Override
    public void refreshAwardMonthUnsettle(String userId, BigDecimal count) {
        AwardMonth condition = new AwardMonth();
        condition.setUserId(userId);
        condition.setNow(new Date());
        AwardMonth data = awardMonthDAO.select(condition);
        data.setNextUnsettleCount(data.getNextUnsettleCount().add(count));
        awardMonthDAO.updateUnsettle(data);

    }

    @Override
    public void addNewAwardMonth(String userId) {
        AwardMonth data = new AwardMonth();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        Date date = calendar.getTime();
        // 查询上个月是否有记录
        AwardMonth condition = new AwardMonth();
        condition.setUserId(userId);
        condition.setNow(date);
        AwardMonth awardMonth = awardMonthDAO.select(condition);
        if (awardMonth != null) {
            data.setUnsettleCount(awardMonth.getNextUnsettleCount());
        } else {
            data.setUnsettleCount(BigDecimal.ZERO);
        }
        data.setUserId(userId);
        data.setStartDate(DateUtil.getCurrentMonthFirstDay());
        data.setEndDate(DateUtil.getCurrentMonthLastDay());
        awardMonthDAO.insert(data);
    }

    @Override
    public List<AwardMonth> queryAwardMonthList(AwardMonth condition) {
        return awardMonthDAO.selectList(condition);
    }

}
