/**
 * @Title EthAddressAOImpl.java 
 * @Package com.cdkj.coin.ao.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年10月27日 下午5:43:34 
 * @version V1.0   
 */
package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.WalletUtils;

import com.cdkj.coin.wallet.ao.IEthAddressAO;
import com.cdkj.coin.wallet.bo.ICollectionBO;
import com.cdkj.coin.wallet.bo.ICtqBO;
import com.cdkj.coin.wallet.bo.IEthAddressBO;
import com.cdkj.coin.wallet.bo.IWithdrawBO;
import com.cdkj.coin.wallet.bo.base.Paginable;
import com.cdkj.coin.wallet.enums.EAddressType;
import com.cdkj.coin.wallet.enums.EBoolean;
import com.cdkj.coin.wallet.enums.EOriginialCoin;
import com.cdkj.coin.wallet.enums.ESysUser;
import com.cdkj.coin.wallet.enums.ESystemAccount;
import com.cdkj.coin.wallet.enums.EWAddressStatus;
import com.cdkj.coin.wallet.enums.EYAddressStatus;
import com.cdkj.coin.wallet.ethereum.EthAddress;
import com.cdkj.coin.wallet.exception.BizException;
import com.cdkj.coin.wallet.exception.EBizErrorCode;

/** 
 * @author: haiqingzheng 
 * @since: 2017年10月27日 下午5:43:34 
 * @history:
 */
@Service
public class EthAddressAOImpl implements IEthAddressAO {

    @Autowired
    private IEthAddressBO ethAddressBO;

    @Autowired
    private ICollectionBO collectionBO;

    @Autowired
    private IWithdrawBO withdrawBO;

    @Autowired
    private ICtqBO ctqBO;

    @Override
    public void addEthAddress(String address, String label, String userId,
            String smsCaptcha, String isCerti, String tradePwd,
            String googleCaptcha) {

        // 地址有效性校验
        if (!WalletUtils.isValidAddress(address)) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "地址" + address + "不符合以太坊规则，请仔细核对");
        }

        List<String> typeList = new ArrayList<String>();
        typeList.add(EAddressType.X.getCode());
        typeList.add(EAddressType.M.getCode());
        typeList.add(EAddressType.W.getCode());

        EthAddress condition = new EthAddress();
        condition.setAddress(address);
        condition.setTypeList(typeList);

        if (ethAddressBO.getTotalCount(condition) > 0) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "提现地址已经在本平台被使用，请仔细核对！");
        }

        String status = EYAddressStatus.NORMAL.getCode();
        // 是否设置为认证账户
        if (EBoolean.YES.getCode().equals(isCerti)) {
            status = EYAddressStatus.CERTI.getCode();
        }

        ethAddressBO.saveEthAddress(EAddressType.Y, userId, address, label,
            null, BigDecimal.ZERO, status, null, null);

    }

    @Override
    public void abandonAddress(String code) {
        EthAddress ethAddress = ethAddressBO.getEthAddress(code);
        if (EWAddressStatus.INVALID.getCode().equals(ethAddress.getStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "地址已失效，无需重复弃用");
        }
        ethAddressBO.abandonAddress(ethAddress);
    }

    @Override
    public EAddressType getType(String address) {
        EAddressType type = EAddressType.Y;
        EthAddress condition = new EthAddress();
        condition.setAddress(address);
        List<EthAddress> results = ethAddressBO.queryEthAddressList(condition);
        if (CollectionUtils.isNotEmpty(results)) {
            EthAddress ethAddress = results.get(0);
            type = EAddressType.getAddressType(ethAddress.getType());
        }
        return type;
    }

    @Override
    public String generateMAddress() {
        String address = ethAddressBO.generateAddress(EAddressType.M,
            ESysUser.SYS_USER.getCode(),
            ESystemAccount.SYS_ACOUNT_ETH.getCode());
        // 通知橙提取
        ctqBO.uploadEthAddress(address, EAddressType.M.getCode());
        return address;
    }

    @Override
    public String importWAddress(String address) {
        if (ethAddressBO.isEthAddressExist(address)) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "地址" + address + "已经在平台内被使用，请仔细核对");
        }
        // 地址有效性校验
        if (!WalletUtils.isValidAddress(address)) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "地址" + address + "不符合以太坊规则，请仔细核对");
        }
        return ethAddressBO.saveEthAddress(EAddressType.W,
            ESysUser.SYS_USER_COLD.getCode(),
            ESystemAccount.SYS_ACOUNT_ETH_COLD.getCode(), address, null,
            BigDecimal.ZERO, EWAddressStatus.NORMAL.getCode(), null, null);
    }

    @Override
    @Transactional
    public Paginable<EthAddress> queryEthAddressPage(int start, int limit,
            EthAddress condition) {
        Paginable<EthAddress> results = ethAddressBO.getPaginable(start, limit,
            condition);
        for (EthAddress ethAddress : results.getList()) {
            // 归集地址统计
            if (EAddressType.W.getCode().equals(ethAddress.getType())) {
                EthAddress xAddress = collectionBO.getAddressUseInfo(
                    ethAddress.getAddress(), EOriginialCoin.ETH.getCode());
                ethAddress.setUseCount(xAddress.getUseCount());
                ethAddress.setUseAmount(xAddress.getUseAmount());
            }
            // 散取地址统计
            if (EAddressType.M.getCode().equals(ethAddress.getType())) {
                EthAddress xAddress = withdrawBO.getAddressUseInfo(
                    ethAddress.getAddress(), EOriginialCoin.ETH.getCode());
                ethAddress.setUseCount(xAddress.getUseCount());
                ethAddress.setUseAmount(xAddress.getUseAmount());
            }
        }
        return results;
    }

    @Override
    public EthAddress getEthAddress(String code) {
        return ethAddressBO.getEthAddress(code);
    }

    @Override
    public BigDecimal getTotalBalance(EAddressType type) {
        return ethAddressBO.getTotalBalance(type);
    }

}
