/**
 * @Title IAwardBO.java 
 * @Package com.ogc.standard.bo 
 * @Description 
 * @author taojian  
 * @date 2018年9月14日 下午4:52:24 
 * @version V1.0   
 */
package com.ogc.standard.bo;

import java.math.BigDecimal;
import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Award;
import com.ogc.standard.domain.TradeOrder;
import com.ogc.standard.domain.User;

/** 
 * @author: taojian 
 * @since: 2018年9月14日 下午4:52:24 
 * @history:
 */
public interface IAwardBO extends IPaginableBO<Award> {
    public Award getAward(Long id);

    // 推荐用户交易分成
    public void saveTradeAward(String refereeUserId, String userKind,
            String refCode, String refNote, BigDecimal tradeCount);

    public void saveOTCAward(String refereeUserId, String userKind,
            String refCode, String refNote, BigDecimal tradeCount);

    public void saveRegistAward(String userId, String userKind, String refCode,
            String refNote);

    public void saveSpecialAward(User user, BigDecimal count, String remark);

    public void refreshStatus(Award data, String isSettle, String remark);

    public BigDecimal count(Award condition);

    public BigDecimal tradeCount(TradeOrder condition);

    public List<Award> queryAwardList(Award condition);

    public Award getAwardByRefCode(String refCode);

}
