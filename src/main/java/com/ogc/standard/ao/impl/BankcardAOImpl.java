package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.IBankcardAO;
import com.ogc.standard.bo.IBankcardBO;
import com.ogc.standard.bo.ISYSUserBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.Bankcard;
import com.ogc.standard.dto.req.XN802020Req;
import com.ogc.standard.dto.req.XN802022Req;
import com.ogc.standard.dto.req.XN802023Req;
import com.ogc.standard.enums.EBankcardStatus;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.ESystemCode;
import com.ogc.standard.exception.BizException;

/**
 * 
 * @author: lei 
 * @since: 2018年9月11日 下午5:41:20 
 * @history:
 */
@Service
public class BankcardAOImpl implements IBankcardAO {

    @Autowired
    private IBankcardBO bankcardBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private ISYSUserBO sysUserBO;

    @Override
    public String addBankcard(XN802020Req req) {
        // 判断卡号是否重复
        // List<Bankcard> list = bankcardBO.queryBankcardList(req.getUserId(),
        // req.getSystemCode());
        // if (CollectionUtils.isNotEmpty(list)) {
        // throw new BizException("xn0000", "您已绑定银行卡,无需绑定多张");
        // }

        Bankcard data = new Bankcard();
        data.setBankcardNumber(req.getBankcardNumber());
        data.setBankCode(req.getBankCode());
        data.setBankName(req.getBankName());
        data.setSubbranch(req.getSubbranch());
        data.setBindMobile(req.getBindMobile());
        data.setUserId(req.getUserId());
        data.setRealName(req.getRealName());
        data.setType(req.getType());
        data.setStatus(EBankcardStatus.UNUSED.getCode());
        data.setCurrency(ECurrency.CNY.getCode());
        data.setAmount(StringValidater.toBigDecimal(req.getAmount()));
        data.setFrozenAmount(BigDecimal.ZERO);
        data.setRemark(req.getRemark());
        data.setSystemCode(ESystemCode.MISS.getCode());
        return bankcardBO.saveBankcard(data);
    }

    @Override
    public void onOffBankcard(String code) {
        bankcardBO.refreshStatusBankcard(code);
    }

    @Override
    public void dropBankcard(String code) {
        if (!bankcardBO.isBankcardExist(code)) {
            throw new BizException(EErrorCode_main.code_NOTEXIST.getCode());
        }
        bankcardBO.removeBankcard(code);
    }

    @Override
    public void editBankcard(XN802022Req req) {
        Bankcard bankcard = bankcardBO.getBankcard(req.getCode());
        if (!bankcard.getBankcardNumber().equals(req.getBankcardNumber())) { // 有修改就去判断是否唯一
            List<Bankcard> list = bankcardBO.queryBankcardList(
                bankcard.getUserId(), bankcard.getSystemCode());
            for (Bankcard card : list) {
                if (req.getBankcardNumber().equals(card.getBankcardNumber())) {
                    throw new BizException(
                        EErrorCode_main.ban_BANKCARD.getCode());
                }
            }
        }
        Bankcard data = new Bankcard();
        data.setCode(req.getCode());
        // 户名有传就修改，不传不修改
        if (StringUtils.isBlank(req.getRealName())) {
            data.setRealName(bankcard.getRealName());
        } else {
            data.setRealName(req.getRealName());
        }
        data.setBankcardNumber(req.getBankcardNumber());
        data.setBankCode(req.getBankCode());
        data.setBankName(req.getBankName());
        data.setSubbranch(req.getSubbranch());
        data.setBindMobile(req.getBindMobile());
        data.setStatus(req.getStatus());
        data.setRemark(req.getRemark());
        bankcardBO.refreshBankcard(data);
    }

    @Override
    public void editBankcard(XN802023Req req) {
        Bankcard bankcard = bankcardBO.getBankcard(req.getCode());
        userBO.checkTradePwd(bankcard.getUserId(), req.getTradePwd());
        if (!bankcard.getBankcardNumber().equals(req.getBankcardNumber())) { // 有修改就去判断是否唯一
            List<Bankcard> list = bankcardBO.queryBankcardList(
                bankcard.getUserId(), bankcard.getSystemCode());
            for (Bankcard card : list) {
                if (req.getBankcardNumber().equals(card.getBankcardNumber())) {
                    throw new BizException(
                        EErrorCode_main.ban_ISEXIST.getCode());
                }
            }
        }
        Bankcard data = new Bankcard();
        // 户名有传就修改，不传不修改
        if (StringUtils.isBlank(req.getRealName())) {
            data.setRealName(bankcard.getRealName());
        } else {
            data.setRealName(req.getRealName());
        }
        data.setCode(req.getCode());
        data.setBankcardNumber(req.getBankcardNumber());
        data.setBankCode(req.getBankCode());
        data.setBankName(req.getBankName());
        data.setSubbranch(req.getSubbranch());
        data.setBindMobile(req.getBindMobile());
        data.setStatus(req.getStatus());
        data.setRemark(req.getRemark());
        bankcardBO.refreshBankcard(data);
    }

    @Override
    public Paginable<Bankcard> queryBankcardPage(int start, int limit,
            Bankcard condition) {
        Paginable<Bankcard> page = bankcardBO.getPaginable(start, limit,
            condition);

        // if (null != page) {
        // for (Bankcard bankcard : page.getList()) {
        // if (!ESysUser.SYS_USER.getCode().equals(bankcard.getUserId())) {
        // User user = userBO.getUser(bankcard.getUserId());
        // bankcard.setUserInfo(user);
        // }
        // }
        // }

        return page;
    }

    @Override
    public List<Bankcard> queryBankcardList(Bankcard condition) {
        return bankcardBO.queryBankcardList(condition);
    }

    @Override
    public Bankcard getBankcard(String code) {
        Bankcard bankcard = bankcardBO.getBankcard(code);
        // if (!ESysUser.SYS_USER.getCode().equals(bankcard.getUserId())) {
        // User user = userBO.getUser(bankcard.getUserId());
        // bankcard.setUserInfo(user);
        // }
        return bankcard;
    }

}
