package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ICoinBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.dao.ICoinDAO;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.enums.ECoinStatus;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

@Component
public class CoinBOImpl extends PaginableBOImpl<Coin> implements ICoinBO {

    @Autowired
    private ICoinDAO coinDAO;

    @Override
    public boolean isCoinExist(long id) {
        Coin condition = new Coin();
        condition.setId(id);
        if (coinDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isContractAddressExist(String type, String contractAddress) {
        Coin condition = new Coin();
        condition.setContractAddress(contractAddress);
        condition.setType(type);
        if (coinDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String saveCoin(Coin data) {
        String code = null;
        if (data != null) {
            coinDAO.insert(data);
        }
        return code;
    }

    @Override
    public int refreshCoin(Coin data) {
        int count = 0;
        if (data != null && StringUtils.isNotBlank(data.getSymbol())) {
            count = coinDAO.update(data);
        }
        return count;
    }

    @Override
    public List<Coin> queryCoinList(Coin condition) {
        return coinDAO.selectList(condition);
    }

    @Override
    public List<Coin> queryOpenCoinList() {
        Coin condition = new Coin();
        condition.setStatus(ECoinStatus.PUBLISHED.getCode());
        condition.setOrder("order_no", true);
        return coinDAO.selectList(condition);
    }

    @Override
    public Coin getCoin(long id) {
        Coin data = null;
        Coin condition = new Coin();
        condition.setId(id);

        data = coinDAO.select(condition);
        if (data == null) {
            throw new BizException(EErrorCode_main.coin_UNSUPPORT.getCode());
        }
        return data;
    }

    @Override
    public Coin getCoin(String symbol) {
        Coin condition = new Coin();
        condition.setSymbol(symbol);
        Coin data = coinDAO.select(condition);
        if (data == null) {
            throw new BizException(EErrorCode_main.coin_UNSUPPORT.getCode());
        }
        return data;

    }

    @Override
    public int refreshStatus(Coin coin, ECoinStatus status, String updater,
            String remark) {
        int count = 0;
        if (coin != null && StringUtils.isNotBlank(coin.getSymbol())) {
            coin.setStatus(status.getCode());
            coin.setUpdater(updater);
            coin.setUpdateDatetime(new Date());
            coin.setRemark(remark);
            count = coinDAO.updateStatus(coin);
        }
        return count;
    }

    @Override
    public void checkCoinCount(String type) {
        Coin condition = new Coin();
        // condition.setType(type);
        condition.setStatus(ECoinStatus.PUBLISHED.getCode());
        if (coinDAO.selectTotalCount(condition) <= 1) {
            throw new BizException(EErrorCode_main.coin_ONESYMBOL.getCode());
        }
    }

    @Override
    public boolean isSymbolExist(String symbol) {
        Coin condition = new Coin();
        condition.setSymbol(symbol);
        Coin data = coinDAO.select(condition);
        if (data == null) {
            return false;
        } else
            return true;
    }

}
