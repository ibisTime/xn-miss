package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IBtcXAddressBO;
import com.ogc.standard.bo.IEthXAddressBO;
import com.ogc.standard.bo.IJourBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.AccountUtil;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IAccountDAO;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.domain.HLOrder;
import com.ogc.standard.enums.EAccountStatus;
import com.ogc.standard.enums.EAccountType;
import com.ogc.standard.enums.EChannelType;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.enums.EJourBizTypeUser;
import com.ogc.standard.enums.ESysUser;
import com.ogc.standard.exception.BizException;

/**
 * @author: xieyj 
 * @since: 2016年12月23日 下午5:24:53 
 * @history:
 */
@Component
public class AccountBOImpl extends PaginableBOImpl<Account>
        implements IAccountBO {
    @Autowired
    private IAccountDAO accountDAO;

    @Autowired
    private IJourBO jourBO;

    @Autowired
    private IEthXAddressBO ethXAddressBO;

    @Autowired
    private IBtcXAddressBO btcXAddressBO;

    @Override
    public String distributeAccount(String userId, EAccountType accountType,
            Coin coin, String address) {
        String accountNumber = null;
        if (StringUtils.isNotBlank(userId)) {

            accountNumber = OrderNoGenerater
                .generate(EGeneratePrefix.Account.getCode());
            Account data = new Account();
            data.setAccountNumber(accountNumber);
            data.setUserId(userId);
            data.setAddress(address);
            data.setType(accountType.getCode());
            data.setCurrency(coin.getSymbol());
            data.setStatus(EAccountStatus.NORMAL.getCode());
            data.setAmount(BigDecimal.ZERO);
            data.setFrozenAmount(BigDecimal.ZERO);

            data.setMd5(AccountUtil.md5(data.getAmount()));
            data.setInAmount(BigDecimal.ZERO);
            data.setOutAmount(BigDecimal.ZERO);
            data.setCreateDatetime(new Date());

            accountDAO.insert(data);

        }
        return accountNumber;
    }

    @Override
    public Account changeAmount(Account dbAccount, BigDecimal transAmount,
            EChannelType channelType, String channelOrder, String refNo,
            String bizType, String bizNote) {

        // 如果变动金额为0，直接返回
        if (transAmount.compareTo(BigDecimal.ZERO) == 0) {
            return dbAccount;
        }
        // 金额变动之后可用余额
        BigDecimal avaliableAmount = dbAccount.getAmount()
            .subtract(dbAccount.getFrozenAmount()).add(transAmount);
        if (!dbAccount.getUserId().startsWith(ESysUser.SYS_USER.getCode())
                && avaliableAmount.compareTo(BigDecimal.ZERO) == -1) {// 特定账户余额可为负
            throw new BizException(
                EErrorCode_main.account_PERSONALLEFT.getCode());
        }
        BigDecimal nowAmount = dbAccount.getAmount().add(transAmount);

        // 记录流水
        String lastOrder = jourBO.addJour(dbAccount, channelType, channelOrder,
            refNo, bizType, bizNote, transAmount);

        // 更改余额
        dbAccount.setAmount(nowAmount);
        dbAccount.setMd5(AccountUtil.md5(dbAccount.getMd5(),
            dbAccount.getAmount(), nowAmount));

        // 统计累计充值金额
        dbAccount.setInAmount(dbAccount.getInAmount());
        if (EJourBizTypeUser.AJ_CHARGE.getCode().equals(bizType)) {
            dbAccount.setInAmount(dbAccount.getInAmount().add(transAmount));
        }
        dbAccount.setLastOrder(lastOrder);
        accountDAO.updateAmount(dbAccount);
        return dbAccount;
    }

    @Override
    public void changeAmountNotJour(String accountNumber,
            BigDecimal transAmount, String lastOrder) {
        Account dbAccount = this.getAccount(accountNumber);
        BigDecimal nowAmount = dbAccount.getAmount().add(transAmount);
        if (!dbAccount.getUserId().startsWith("SYS_USER")
                && nowAmount.compareTo(BigDecimal.ZERO) == -1) {
            throw new BizException(
                EErrorCode_main.account_PERSONALLEFT.getCode());
        }
        // 更改余额
        Account data = new Account();
        data.setAccountNumber(accountNumber);
        data.setAmount(nowAmount);
        data.setMd5(AccountUtil.md5(dbAccount.getMd5(), dbAccount.getAmount(),
            nowAmount));

        data.setInAmount(dbAccount.getInAmount());
        data.setLastOrder(lastOrder);
        accountDAO.updateAmount(data);
    }

    @Override
    public void changeAmountForHL(HLOrder order) {
        Account dbAccount = this.getAccount(order.getAccountNumber());
        BigDecimal nowAmount = dbAccount.getAmount().add(order.getAmount());
        // 记录流水
        String lastOrder = jourBO.addJourForHL(dbAccount, order, "");
        // 更改余额
        Account data = new Account();
        data.setAccountNumber(dbAccount.getAccountNumber());
        data.setAmount(nowAmount);
        data.setMd5(AccountUtil.md5(dbAccount.getMd5(), dbAccount.getAmount(),
            nowAmount));
        data.setInAmount(dbAccount.getInAmount());
        data.setLastOrder(lastOrder);
        accountDAO.updateAmount(data);
    }

    @Override
    public Account frozenAmount(Account dbAccount, BigDecimal freezeAmount,
            String bizType, String bizNote, String refNo) {
        if (freezeAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return dbAccount;
        }
        BigDecimal avaliableAmount = dbAccount.getAmount()
            .subtract(dbAccount.getFrozenAmount()).subtract(freezeAmount);
        if (avaliableAmount.compareTo(BigDecimal.ZERO) == -1) {
            throw new BizException(
                EErrorCode_main.account_PERSONALLEFT.getCode());
        }
        // 记录冻结流水
        String lastOrder = jourBO.addFrozenJour(dbAccount, EChannelType.NBZ,
            null, refNo, bizType, bizNote, freezeAmount);
        BigDecimal nowFrozenAmount = dbAccount.getFrozenAmount()
            .add(freezeAmount);
        dbAccount.setAccountNumber(dbAccount.getAccountNumber());
        dbAccount.setFrozenAmount(nowFrozenAmount);
        dbAccount.setLastOrder(lastOrder);
        accountDAO.frozenAmount(dbAccount);
        return dbAccount;
    }

    @Override
    public Account unfrozenAmount(Account dbAccount, BigDecimal unfreezeAmount,
            String bizType, String bizNote, String refNo) {
        if (unfreezeAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return dbAccount;
        }
        BigDecimal nowFrozenAmount = dbAccount.getFrozenAmount()
            .subtract(unfreezeAmount);
        if (nowFrozenAmount.compareTo(BigDecimal.ZERO) == -1) {
            throw new BizException(
                EErrorCode_main.account_UNFROZENZERO.getCode());
        }

        // 记录流水
        String lastOrder = jourBO.addFrozenJour(dbAccount, EChannelType.NBZ,
            null, refNo, bizType, bizNote, unfreezeAmount.negate());
        dbAccount.setFrozenAmount(nowFrozenAmount);
        dbAccount.setLastOrder(lastOrder);
        accountDAO.unfrozenAmount(dbAccount);
        return dbAccount;
    }

    @Override
    public void refreshStatus(String accountNumber, EAccountStatus status) {
        if (StringUtils.isNotBlank(accountNumber)) {
            Account data = new Account();
            data.setAccountNumber(accountNumber);
            data.setStatus(status.getCode());
            accountDAO.updateStatus(data);
        }
    }

    @Override
    public Account getAccount(String accountNumber) {
        Account data = null;
        if (StringUtils.isNotBlank(accountNumber)) {
            Account condition = new Account();
            condition.setAccountNumber(accountNumber);
            data = accountDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.account_EXIST.getCode());
            }
        }
        return data;
    }

    @Override
    public List<Account> queryAccountList(Account data) {
        return accountDAO.selectList(data);
    }

    @Override
    public List<Account> queryAccountList(String userId) {
        Account condition = new Account();
        condition.setUserId(userId);
        return accountDAO.selectList(condition);
    }

    /**
    * @see com.std.account.bo.IAccountBO#getAccountByUser(java.lang.String,
    java.lang.String, java.lang.String)
    */
    @Override
    public Account getAccountByUser(String userId, String currency) {
        Account data = null;
        if (StringUtils.isNotBlank(userId)
                && StringUtils.isNotBlank(currency)) {
            Account condition = new Account();
            condition.setUserId(userId);
            condition.setCurrency(currency);
            data = accountDAO.select(condition);
            if (data == null) {
                throw new BizException(
                    EErrorCode_main.account_NOTEXIST.getCode(), userId,
                    currency);
            }
        }
        return data;
    }

    /**
    * @see com.std.account.bo.IAccountBO#getSysAccount(java.lang.String,
    java.lang.String)
    */
    @Override
    public Account getSysAccountNumber(String systemCode, String companyCode,
            ECurrency currency) {
        Account condition = new Account();
        // 平台账户只有一类,类型+币种+公司+系统=唯一系统账户
        condition.setType(EAccountType.Plat.getCode());
        condition.setCurrency(currency.getCode());
        return accountDAO.select(condition);
    }

    @Override
    public void transAmount(String fromUserId, String fromCurrency,
            String toUserId, String toCurrency, BigDecimal transAmount,
            String fromBizType, String toBizType, String fromBizNote,
            String toBizNote, String refNo) {
        Account fromAccount = this.getAccountByUser(fromUserId, fromCurrency);
        Account toAccount = this.getAccountByUser(toUserId, toCurrency);
        transAmount(fromAccount, toAccount, transAmount, fromBizType, toBizType,
            fromBizNote, toBizNote, refNo);
    }

    @Override
    public void transAmount(Account fromAccount, Account toAccount,
            BigDecimal transAmount, String fromBizType, String toBizType,
            String fromBizNote, String toBizNote, String refNo) {
        String fromAccountNumber = fromAccount.getAccountNumber();
        String toAccountNumber = toAccount.getAccountNumber();
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new BizException(EErrorCode_main.account_SAME.getCode());
        }
        this.changeAmount(fromAccount, transAmount.negate(), EChannelType.NBZ,
            null, refNo, fromBizType, fromBizNote);
        this.changeAmount(toAccount, transAmount, EChannelType.NBZ, null, refNo,
            toBizType, toBizNote);
    }

    @Override
    public boolean isAccountExist(String userId, String currency) {
        boolean flag = false;
        Account condition = new Account();
        condition.setUserId(userId);
        condition.setCurrency(currency);
        if (this.getTotalCount(condition) > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void distributePlatAccount(String symbol) {
        if (StringUtils.isNotBlank(symbol)) {

            savePlatAccount("SYS_ACOUNT_" + symbol, ESysUser.SYS_USER,
                "平台" + symbol + "盈亏账户", symbol);

            savePlatAccount("SYS_ACOUNT_COLD_" + symbol + "", ESysUser.SYS_USER,
                "平台" + symbol + "冷钱包", symbol);

        }
    }

    @Override
    public Account savePlatAccount(String accountNumber, ESysUser sysUser,
            String accountName, String currency) {
        Account data = new Account();

        data.setAccountNumber(accountNumber);
        data.setUserId(sysUser.getCode());
        data.setType(EAccountType.Plat.getCode());
        data.setStatus(EAccountStatus.NORMAL.getCode());
        data.setCurrency(currency);

        data.setAmount(BigDecimal.ZERO);
        data.setFrozenAmount(BigDecimal.ZERO);
        data.setMd5(AccountUtil.md5(data.getAmount()));
        data.setInAmount(BigDecimal.ZERO);

        data.setOutAmount(BigDecimal.ZERO);
        data.setCreateDatetime(new Date());

        accountDAO.insert(data);
        return data;
    }

}
