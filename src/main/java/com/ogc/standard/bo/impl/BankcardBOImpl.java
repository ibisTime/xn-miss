package com.ogc.standard.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.IBankcardBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.IBankCardDAO;
import com.ogc.standard.domain.Bankcard;
import com.ogc.standard.enums.EBoolean;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.exception.BizException;

/**
 * 
 * @author: lei 
 * @since: 2018年9月11日 下午5:41:37 
 * @history:
 */
@Component
public class BankcardBOImpl extends PaginableBOImpl<Bankcard>
        implements IBankcardBO {

    @Autowired
    private IBankCardDAO bankcardDAO;

    @Override
    public boolean isBankcardExist(String code) {
        Bankcard condition = new Bankcard();
        condition.setCode(code);
        if (bankcardDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String saveBankcard(Bankcard data) {
        String code = null;
        if (data != null) {
            code = OrderNoGenerater
                .generate(EGeneratePrefix.BANK_CARD.getCode());
            data.setCode(code);
            data.setStatus(EBoolean.YES.getCode());
            data.setCreateDatetime(new Date());
            bankcardDAO.insert(data);
        }
        return code;
    }

    @Override
    public int removeBankcard(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Bankcard data = new Bankcard();
            data.setCode(code);
            count = bankcardDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshBankcard(Bankcard data) {
        int count = 0;
        if (StringUtils.isNotBlank(data.getCode())) {
            count = bankcardDAO.update(data);
        }
        return count;
    }

    @Override
    public List<Bankcard> queryBankcardList(Bankcard condition) {
        return bankcardDAO.selectList(condition);
    }

    @Override
    public Bankcard getBankcard(String code) {
        Bankcard data = null;
        if (StringUtils.isNotBlank(code)) {
            Bankcard condition = new Bankcard();
            condition.setCode(code);
            data = bankcardDAO.select(condition);
            if (data == null) {
                throw new BizException(EErrorCode_main.ban_BANKCARD.getCode());
            }
        }
        return data;
    }

    @Override
    public Bankcard getBankcardInfo(String code) {
        Bankcard data = null;
        if (StringUtils.isNotBlank(code)) {
            Bankcard condition = new Bankcard();
            condition.setCode(code);
            data = bankcardDAO.select(condition);
        }
        return data;
    }

    /** 
     * @see com.std.account.bo.IBankcardBO#getBankcardByBankcardNumber(java.lang.String)
     */
    @Override
    public Bankcard getBankcardByBankcardNumber(String bankcardNumber) {
        Bankcard data = null;
        if (StringUtils.isNotBlank(bankcardNumber)) {
            Bankcard condition = new Bankcard();
            condition.setBankcardNumber(bankcardNumber);
            List<Bankcard> list = bankcardDAO.selectList(condition);
            if (CollectionUtils.isNotEmpty(list)) {
                data = list.get(0);
            }
        }
        return data;
    }

    @Override
    public List<Bankcard> queryBankcardList(String userId) {
        Bankcard condition = new Bankcard();
        condition.setUserId(userId);
        return bankcardDAO.selectList(condition);
    }

    @Override
    public List<Bankcard> queryBankcardList(String userId, String bankCode) {
        Bankcard condition = new Bankcard();
        condition.setUserId(userId);
        condition.setBankCode(bankCode);
        return bankcardDAO.selectList(condition);
    }

    @Override
    public int getTotalBankcard(String userId, String bankCode) {
        Bankcard condition = new Bankcard();
        condition.setUserId(userId);
        condition.setBankCode(bankCode);
        return (int) bankcardDAO.selectTotalCount(condition);
    }
}
