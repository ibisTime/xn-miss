package com.ogc.standard.bo;

import java.math.BigDecimal;
import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Account;
import com.ogc.standard.enums.EAccountStatus;
import com.ogc.standard.enums.EAccountType;
import com.ogc.standard.enums.EChannelType;
import com.ogc.standard.enums.ECurrency;

/**
 * @author: xieyj
 * @since: 2016年11月11日 上午11:23:06 
 * @history:
 */
public interface IAccountBO extends IPaginableBO<Account> {

    // 分配B端用户账户
    public String distributeAccount(String userId, String realName,
            EAccountType accountType, String currency, String systemCode,
            String companyCode);

    // 分配C端用户的账户
    public String distributeAccount(String userId, String mobile, String type,
            String currency);

    // 变更账户余额：流水落地
    public void changeAmount(String accountNumber, EChannelType channelType,
            String channelOrder, String payGroup, String refNo, String bizType,
            String bizNote, BigDecimal transAmount);

    // 仅变更账户余额：流水不落地
    public void changeAmountNotJour(String accountNumber,
            BigDecimal transAmount, String lastOrder);

    // 冻结金额（余额变动）
    public void frozenAmount(Account dbAccount, BigDecimal freezeAmount,
            String withdrawCode);

    // 解冻账户(冻结金额原路返回)
    public void unfrozenAmount(Account dbAccount, BigDecimal freezeAmount,
            String withdrawCode);

    // 扣减冻结金额
    public void cutFrozenAmount(Account dbAccount, BigDecimal amount);

    // 内部转账
    public void transAmountCZB(String fromUserId, String fromCurrency,
            String toUserId, String toCurrency, BigDecimal transAmount,
            String fromBizType, String toBizType, String fromBizNote,
            String toBizNote, String refNo);

    // 更新户名
    public void refreshAccountName(String userId, String realName);

    // 更新账户状态
    public void refreshStatus(String accountNumber, EAccountStatus status);

    // 获取账户
    public Account getAccount(String accountNumber);

    // 通过用户编号和币种获取账户
    public Account getAccountByUser(String userId, String currency);

    // 根据系统编号,公司编号和币种获取对应的系统账户(账户类型确定为系统账户)
    public Account getSysAccountNumber(ECurrency currency);

    // 获取账户列表
    public List<Account> queryAccountList(Account data);

    // 查询各个端人民币账户总余额
    public List<Account> queryAccountAmountSumList(Account condition);
}
