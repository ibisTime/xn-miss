package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.IAccountAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IBtcXAddressBO;
import com.ogc.standard.bo.ICoinBO;
import com.ogc.standard.bo.IEthXAddressBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.domain.User;
import com.ogc.standard.enums.EAccountType;
import com.ogc.standard.enums.ECoinType;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.ESysUser;
import com.ogc.standard.exception.BizException;

@Service
public class AccountAOImpl implements IAccountAO {

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IEthXAddressBO ethXAddressBO;

    @Autowired
    private IBtcXAddressBO btcXAddressBO;

    // @Autowired
    // private ICtqBO ctqBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private ICoinBO coinBO;

    @Override
    @Transactional
    public void distributeAccount(String userId) {

        // 获取平台开启的币种列表
        List<Coin> coins = coinBO.queryOpenCoinList();

        // 获取用户已经创建的虚拟账户
        List<Account> accounts = accountBO.queryAccountList(userId);

        // 移除已经创建账户的币种
        coins = removeExistAccountCoins(coins, accounts);
        if (CollectionUtils.isNotEmpty(coins)) {
            String ethXAddress = null;
            String btcXAddress = null;
            for (Coin coin : coins) {
                // 有几个币种创建几个账户，有几个原生币就加几个地址（代币用原生币地址）
                if (ECoinType.ETH.getCode().equals(coin.getType())
                        || ECoinType.X.getCode().equals(coin.getType())) {
                    if (ethXAddress == null) {
                        ethXAddress = ethXAddressBO.generateAddress(userId);
                    }
                    accountBO.distributeAccount(userId, EAccountType.Customer,
                        coin, ethXAddress);
                } else if (ECoinType.BTC.getCode().equals(coin.getType())) {
                    if (btcXAddress == null) {
                        btcXAddress = btcXAddressBO.generateAddress(userId);
                    }
                    accountBO.distributeAccount(userId, EAccountType.Customer,
                        coin, btcXAddress);
                } else {
                    throw new BizException(
                        EErrorCode_main.coin_UNSUPPORT.getCode());
                }

            }
        }
    }

    private List<Coin> removeExistAccountCoins(List<Coin> coins,
            List<Account> accounts) {
        List<Coin> results = new ArrayList<>();
        for (Coin coin : coins) {
            boolean isExist = false;
            for (Account account : accounts) {
                if (account.getCurrency().equals(coin.getSymbol())) {
                    isExist = true;
                }
            }
            if (!isExist) {
                results.add(coin);
            }
        }
        return results;
    }

    @Override
    public Paginable<Account> queryAccountPage(int start, int limit,
            Account condition) {

        Paginable<Account> page = accountBO.getPaginable(start, limit,
            condition);

        if (null != page) {
            List<Account> list = page.getList();
            for (Account account : list) {
                User user = userBO.getUser(account.getUserId());
                account.setRealName(user.getRealName());
                account.setMobile(user.getMobile());
            }
        }

        return page;
    }

    @Override
    public Account getAccount(String accountNumber) {
        return accountBO.getAccount(accountNumber);
    }

    @Override
    @Transactional
    public List<Account> getAccountByUserId(String userId, String currency) {
        distributeAccount(userId);
        List<Account> accounts = new ArrayList<>();
        if (StringUtils.isBlank(currency)) {
            List<Coin> coins = coinBO.queryOpenCoinList();
            for (Coin coin : coins) {
                Account account = accountBO.getAccountByUser(userId,
                    coin.getSymbol());
                accounts.add(account);
            }
        } else {
            Account account = accountBO.getAccountByUser(userId, currency);
            accounts.add(account);
        }

        return accounts;
    }

    @Override
    public List<Account> queryAccountList(Account condition) {
        return accountBO.queryAccountList(condition);
    }

    @Override
    @Transactional
    public void transAmount(String fromUserId, String fromCurrency,
            String toUserId, String toCurrency, BigDecimal transAmount,
            String fromBizType, String toBizType, String fromBizNote,
            String toBizNote, String refNo) {
        // 检查平台账户是否已存在，如不存在就创建
        checkSystemAccount(fromUserId, fromCurrency);
        checkSystemAccount(toUserId, toCurrency);
        accountBO.transAmount(fromUserId, fromCurrency, toUserId, toCurrency,
            transAmount, fromBizType, toBizType, fromBizNote, toBizNote, refNo);
    }

    private void checkSystemAccount(String userId, String currency) {
        ESysUser sysUser = ESysUser.getDirectionMap().get(userId);
        Coin coin = coinBO.getCoin(currency);
        if (sysUser != null && coin != null) {
            Account condition = new Account();
            condition.setUserId(userId);
            condition.setCurrency(currency);
            if (accountBO.getTotalCount(condition) <= 0) {
                //
                // if (ESysUser.SYS_USER.getCode().equals(userId)) {
                // accountBO.savePlatAccount("SYS_ACOUNT_" + currency,
                // ESysUser.SYS_USER, "平台" + currency + "盈亏账户", currency);
                // } else if (ESysUser.SYS_USER_COLD.getCode().equals(userId)) {
                // accountBO.savePlatAccount(
                // "SYS_ACOUNT_" + currency + "_COLD",
                // ESysUser.SYS_USER_COLD, "平台" + currency + "冷钱包",
                // currency);
                // } else if (ESysUser.SYS_USER_HB.getCode().equals(userId)) {
                // accountBO.savePlatAccount("SYS_ACOUNT_" + currency + "_HB",
                // ESysUser.SYS_USER_HB, "平台" + currency + "红包账户",
                // currency);
                // } else if (ESysUser.SYS_USER_LHLC.getCode().equals(userId)) {
                // accountBO.savePlatAccount(
                // "SYS_ACOUNT_" + currency + "_LHLC",
                // ESysUser.SYS_USER_LHLC, "平台" + currency + "量化理财账户",
                // currency);
                // }

            }
        }
    }

    @Override
    public Account frozenAmount(String userId, String currency,
            BigDecimal freezeAmount, String bizType, String bizNote,
            String refNo) {
        Account dbAccount = accountBO.getAccountByUser(userId, currency);
        return accountBO.frozenAmount(dbAccount, freezeAmount, bizType, bizNote,
            refNo);
    }

    @Override
    public Account unfrozenAmount(String userId, String currency,
            BigDecimal unfreezeAmount, String bizType, String bizNote,
            String refNo) {
        Account dbAccount = accountBO.getAccountByUser(userId, currency);
        return accountBO.unfrozenAmount(dbAccount, unfreezeAmount, bizType,
            bizNote, refNo);
    }

    @Override
    public void transAmount(String fromAddress, String toAddress,
            BigDecimal transAmount, String currency) {
        Account fromAccount = getAccount(fromAddress, currency);
        Account toAccount = getAccount(toAddress, currency);
        if (fromAccount == null || toAccount == null) {
            throw new BizException(EErrorCode_main.account_EXIST.getCode());
        }

        if (transAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException(
                EErrorCode_main.account_TRANSFERAMOUNT.getCode());
        }

        // 账户可用余额是否充足
        if (fromAccount.getAmount().subtract(fromAccount.getFrozenAmount())
            .compareTo(transAmount) == -1) {
            throw new BizException(
                EErrorCode_main.account_PERSONALLEFT.getCode());
        }

        // accountBO.transAmount(fromAccount, toAccount, transAmount,
        // EJourBizTypeUser.AJ_TRANSFER_OUT.getCode(),
        // EJourBizTypeUser.AJ_TRANSFER_IN.getCode(),
        // EJourBizTypeUser.AJ_TRANSFER_OUT.getValue() + "对方账户："
        // + toAccount.getRealName(),
        // EJourBizTypeUser.AJ_TRANSFER_IN.getValue() + "对方账户："
        // + fromAccount.getRealName(),
        // "transfer_order");
    }

    private Account getAccount(String address, String currency) {
        Account account = null;
        Coin coin = coinBO.getCoin(currency);
        // if (ECoinType.ORIGINAL.getCode().equals(coin.getType())) {
        // if (EOriginialCoin.BTC.getCode().equals(coin.getSymbol())) {
        // BtcAddress btcAddress = btcAddressBO
        // .getBtcAddress(EAddressType.X, address);
        // if (btcAddress == null) {
        // throw new BizException(EBizErrorCode.DEFAULT.getCode(),
        // "接收地址不是平台内地址，不能进行内部转账");
        // }
        // account = accountBO.getAccount(btcAddress.getAccountNumber());
        // } else if (EOriginialCoin.ETH.getCode().equals(coin.getSymbol())) {
        // EthAddress ethAddress = ethAddressBO
        // .getEthAddress(EAddressType.X, address);
        // if (ethAddress == null) {
        // throw new BizException(EBizErrorCode.DEFAULT.getCode(),
        // "接收地址不是平台内地址，不能进行内部转账");
        // }
        // account = accountBO.getAccount(ethAddress.getAccountNumber());
        // }
        // } else if (ECoinType.ETH_TOKEN.getCode().equals(coin.getType())) {
        //
        // TokenAddress tokenAddress = null;
        // TokenAddress condition = new TokenAddress();
        // condition.setType(EAddressType.X.getCode());
        // condition.setAddress(address);
        // condition.setSymbol(coin.getSymbol());
        // List<TokenAddress> results = tokenAddressBO
        // .queryTokenAddressList(condition);
        // if (CollectionUtils.isNotEmpty(results)) {
        // tokenAddress = results.get(0);
        // }
        //
        // if (tokenAddress == null) {
        // throw new BizException(EBizErrorCode.DEFAULT.getCode(),
        // "接收地址不是平台内地址，不能进行内部转账");
        // }
        // account = accountBO.getAccount(tokenAddress.getAccountNumber());
        // }
        return account;
    }
}
