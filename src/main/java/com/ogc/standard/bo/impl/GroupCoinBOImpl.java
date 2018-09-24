package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IGroupCoinBO;
import com.ogc.standard.bo.IGroupCoinJourBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IGroupCoinDAO;
import com.ogc.standard.domain.GroupCoin;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.exception.BizException;

@Component
public class GroupCoinBOImpl extends PaginableBOImpl<GroupCoin>
        implements IGroupCoinBO {

    @Autowired
    private IGroupCoinDAO groupCoinDAO;

    @Autowired
    private IGroupCoinJourBO groupCoinJourBO;

    @Override
    public GroupCoin distributeAccount(String userId, String groupCode,
            String symbol, BigDecimal initAmount, BigDecimal totalAssets,
            Double rate) {

        String accountNumber = null;
        accountNumber = OrderNoGenerater
            .generate(EGeneratePrefix.Account.getCode());

        GroupCoin groupCoin = new GroupCoin();
        groupCoin.setAccountNumber(accountNumber);
        groupCoin.setUserId(userId);
        groupCoin.setGroupCode(groupCode);
        groupCoin.setSymbol(symbol);
        groupCoin.setCount(initAmount);

        groupCoin.setFrozenCount(BigDecimal.ZERO);
        groupCoin.setAssets(totalAssets);
        groupCoin.setRate(rate);
        groupCoin.setCreateDatetime(new Date());

        groupCoinDAO.insert(groupCoin);

        return groupCoin;
    }

    @Override
    public void removeGroupCoin(String groupCode, String symbol) {

        // GroupCoin data = this.getGroupCoin(groupCode, symbol);
        // groupCoinDAO.delete(data);

    }

    @Override
    public int refreshGroupCoin(String groupCode, String symbol) {
        int count = 0;

        // GroupCoin data = this.getGroupCoin(groupCode, symbol);

        // count = groupCoinDAO.update(data);
        return count;
    }

    @Override
    public List<GroupCoin> queryGroupCoinListByGroupCode(String groupCode) {

        GroupCoin condition = new GroupCoin();
        condition.setGroupCode(groupCode);
        return groupCoinDAO.selectList(condition);

    }

    @Override
    public GroupCoin getGroupCoin(String groupCode, String userId,
            String symbol) {
        GroupCoin data = null;
        if (StringUtils.isNotBlank(groupCode)
                && StringUtils.isNotBlank(symbol)) {

            GroupCoin condition = new GroupCoin();
            condition.setGroupCode(groupCode);
            condition.setSymbol(symbol);
            condition.setUserId(userId);

            data = groupCoinDAO.select(condition);

            if (data == null) {
                throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
            }

        }
        return data;
    }

    @Override
    public GroupCoin checkAccountAndDistribute(String userId, String groupCode,
            String symbol) {
        GroupCoin data = null;
        if (StringUtils.isNotBlank(groupCode)
                && StringUtils.isNotBlank(symbol)) {

            GroupCoin condition = new GroupCoin();
            condition.setGroupCode(groupCode);
            data = groupCoinDAO.select(condition);

            if (data == null) {
                data = distributeAccount(userId, groupCode, symbol,
                    BigDecimal.ZERO, BigDecimal.ZERO, 0.0);
            }

        }
        return data;
    }

    @Override
    public GroupCoin frozenCount(GroupCoin dbAccount, BigDecimal freezeAmount,
            String bizType, String bizNote, String refNo) {
        if (freezeAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return dbAccount;
        }
        BigDecimal avaliableCount = dbAccount.getCount()
            .subtract(dbAccount.getFrozenCount()).subtract(freezeAmount);
        if (avaliableCount.compareTo(BigDecimal.ZERO) == -1) {
            throw new BizException(
                EErrorCode_main.account_PERSONALLEFT.getCode());
        }

        // 记录冻结流水
        String lastOrder = groupCoinJourBO.addFrozenJour(dbAccount, refNo,
            bizType, bizNote, freezeAmount);

        // 冻结
        BigDecimal nowFrozenAmount = dbAccount.getFrozenCount()
            .add(freezeAmount);
        dbAccount.setAccountNumber(dbAccount.getAccountNumber());
        dbAccount.setFrozenCount(nowFrozenAmount);
        dbAccount.setLastOrder(lastOrder);
        groupCoinDAO.frozenAmount(dbAccount);

        return dbAccount;
    }

    @Override
    public GroupCoin unfrozenAmount(GroupCoin dbAccount,
            BigDecimal unfreezeAmount, String bizType, String bizNote,
            String refNo) {
        if (unfreezeAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return dbAccount;
        }
        BigDecimal nowFrozenCount = dbAccount.getFrozenCount()
            .subtract(unfreezeAmount);
        if (nowFrozenCount.compareTo(BigDecimal.ZERO) == -1) {
            throw new BizException(
                EErrorCode_main.account_UNFROZENZERO.getCode());
        }

        // 记录冻结流水
        String lastOrder = groupCoinJourBO.addFrozenJour(dbAccount, refNo,
            bizType, bizNote, unfreezeAmount.negate());

        dbAccount.setFrozenCount(nowFrozenCount);
        dbAccount.setLastOrder(lastOrder);
        groupCoinDAO.unfrozenAmount(dbAccount);
        return dbAccount;
    }

    @Override
    public GroupCoin changeAmount(GroupCoin dbAccount, BigDecimal transCount,
            String refNo, String bizType, String bizNote) {
        // 如果变动金额为0，直接返回
        if (transCount.compareTo(BigDecimal.ZERO) == 0) {
            return dbAccount;
        }
        // 金额变动之后可用余额
        BigDecimal avaliableAmount = dbAccount.getCount()
            .subtract(dbAccount.getFrozenCount()).add(transCount);
        if (!dbAccount.getUserId().startsWith("SYS_USER")
                && avaliableAmount.compareTo(BigDecimal.ZERO) == -1) {// 特定账户余额可为负
            throw new BizException(
                EErrorCode_main.account_PERSONALLEFT.getCode());
        }
        BigDecimal nowCount = dbAccount.getCount().add(transCount);

        // 记录流水
        String lastOrder = groupCoinJourBO.addJour(dbAccount, refNo, bizType,
            bizNote, transCount);

        // 更改余额
        dbAccount.setCount(nowCount);

        // 统计累计充值金额
        dbAccount.setLastOrder(lastOrder);
        groupCoinDAO.updateAmount(dbAccount);
        return dbAccount;
    }

}
