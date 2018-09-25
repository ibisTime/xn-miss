package com.ogc.standard.ao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.WalletUtils;

import com.ogc.standard.ao.ICoinAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.ICoinBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.CoinUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.dto.req.XN802000Req;
import com.ogc.standard.dto.req.XN802002Req;
import com.ogc.standard.enums.ECoinStatus;
import com.ogc.standard.enums.ECoinType;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.exception.BizException;

@Service
public class CoinAOImpl implements ICoinAO {

    @Autowired
    private ICoinBO coinBO;

    // @Autowired
    // private ICtqBO ctqBO;

    @Autowired
    private IAccountBO accountBO;

    @Override
    @Transactional
    public void addCoinAndPublish(XN802000Req req) {

        if (ECoinType.X.getCode().equals(req.getType())) {
            addEthToken(req);
        } else {
            throw new BizException(EErrorCode_main.coin_UNSUPPORT.getCode());
        }

    }

    private void addEthToken(XN802000Req req) {

        if (!WalletUtils.isValidAddress(req.getContractAddress())) {
            throw new BizException(EErrorCode_main.coin_ADDRESSRULE.getCode(),
                req.getContractAddress());
        }

        // 检查币种符号是否已经在平台内存在
        if (coinBO.isSymbolExist(req.getSymbol())) {
            throw new BizException(EErrorCode_main.coin_SYMBOLUSED.getCode(),
                req.getSymbol());
        }
        // 检查币种符号是否已经在平台内存在
        if (coinBO.isContractAddressExist(ECoinType.X.getCode(),
            req.getContractAddress())) {
            throw new BizException(
                EErrorCode_main.coin_CONTARTADDRESS.getCode(),
                req.getContractAddress());
        }

        // 分配该币种盈亏账户和冷钱包账户
        // accountBO.distributePlatAccount(req.getSymbol());

        // 落地币种信息
        Coin data = new Coin();

        data.setSymbol(req.getSymbol());
        data.setEname(req.getEname());
        data.setCname(req.getCname());
        data.setType(ECoinType.X.getCode());
        data.setUnit(StringValidater.toInteger(req.getUnit()));

        data.setIcon(req.getIcon());
        data.setPic1(req.getPic1());
        data.setPic2(req.getPic2());
        data.setPic3(req.getPic3());
        data.setOrderNo(StringValidater.toInteger(req.getOrderNo()));

        data.setIntroduction(req.getIntroduction());
        data.setTotalSupply(req.getTotalSupply());
        data.setTotalSupplyMarket(req.getTotalSupplyMarket());
        data.setMaxSupply(req.getMaxSupply());
        data.setMaxSupplyMarket(req.getMaxSupplyMarket());

        data.setRank(req.getRank());
        data.setWhitePaper(req.getWhitePaper());
        data.setWebUrl(req.getWebUrl());
        data.setBlockUrl(req.getBlockUrl());
        data.setIcoDatetime(req.getIcoDatetime());

        data.setCollectStart(CoinUtil.toMinUnit(
            StringValidater.toBigDecimal(req.getCollectStart()),
            StringValidater.toInteger(req.getUnit())));
        data.setWithdrawFee(CoinUtil.toMinUnit(
            StringValidater.toBigDecimal(req.getWithdrawFee()),
            StringValidater.toInteger(req.getUnit())));
        data.setContractAddress(req.getContractAddress());
        data.setContractAbi(req.getContractABI());
        data.setStatus(ECoinStatus.PUBLISHED.getCode());

        data.setUpdater(req.getUpdater());
        data.setUpdateDatetime(new Date());
        data.setRemark(req.getRemark());

        coinBO.saveCoin(data);

        // 上传合约地址
        // ctqBO.uploadContractAddress(req.getSymbol(),
        // req.getContractAddress());

    }

    @Override
    public int editCoin(XN802002Req req) {

        Coin data = coinBO.getCoin(StringValidater.toLong(req.getId()));

        data.setId(StringValidater.toLong(req.getId()));
        data.setEname(req.getEname());
        data.setCname(req.getCname());
        data.setIcon(req.getIcon());
        data.setPic1(req.getPic1());

        data.setPic2(req.getPic2());
        data.setPic3(req.getPic3());
        data.setOrderNo(StringValidater.toInteger(req.getOrderNo()));
        data.setIntroduction(req.getIntroduction());
        data.setTotalSupply(req.getTotalSupply());

        data.setTotalSupplyMarket(req.getTotalSupplyMarket());
        data.setMaxSupply(req.getMaxSupply());
        data.setMaxSupplyMarket(req.getMaxSupplyMarket());
        data.setRank(req.getRank());
        data.setWhitePaper(req.getWhitePaper());

        data.setWebUrl(req.getWebUrl());
        data.setBlockUrl(req.getBlockUrl());
        data.setIcoDatetime(req.getIcoDatetime());
        data.setCollectStart(CoinUtil.toMinUnit(
            StringValidater.toBigDecimal(req.getCollectStart()),
            data.getUnit()));
        data.setWithdrawFee(CoinUtil.toMinUnit(
            StringValidater.toBigDecimal(req.getWithdrawFee()),
            data.getUnit()));

        data.setUpdater(req.getUpdater());
        data.setUpdateDatetime(new Date());
        data.setRemark(req.getRemark());

        return coinBO.refreshCoin(data);
    }

    @Override
    public void publish(long id, String updater, String remark) {

        Coin coin = coinBO.getCoin(id);
        if (ECoinStatus.PUBLISHED.getCode().equals(coin.getStatus())) {
            throw new BizException(
                EErrorCode_main.coin_COINPUBLISHED.getCode());
        }

        coinBO.refreshStatus(coin, ECoinStatus.PUBLISHED, updater, remark);

    }

    @Override
    public void revoke(long id, String updater, String remark) {

        Coin coin = coinBO.getCoin(id);
        if (!ECoinStatus.PUBLISHED.getCode().equals(coin.getStatus())) {
            throw new BizException(
                EErrorCode_main.coin_COINUNPUBLISH.getCode());
        }
        // 检查币种类型数量，至少保留一个币种
        coinBO.checkCoinCount(coin.getType());

        coinBO.refreshStatus(coin, ECoinStatus.REVOKE, updater, remark);

    }

    @Override
    public Paginable<Coin> queryCoinPage(int start, int limit, Coin condition) {
        return coinBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<Coin> queryCoinList(Coin condition) {
        return coinBO.queryCoinList(condition);
    }

    @Override
    public Coin getCoin(long id) {
        return coinBO.getCoin(id);
    }

    @Override
    public Coin getCoin(String symbol) {
        return coinBO.getCoin(symbol);
    }

    // @Override
    // public XN802270Res queryCoinBalance(XN802270Req req) {
    //
    // XN802270Res res = new XN802270Res();
    //
    // List<XN802270DetailRes> resultList = new ArrayList<>();
    //
    // for (XN802270DetailReq account : req.getAccountList()) {
    //
    // Coin coin = coinBO.getCoin(account.getSymbol());
    // BigDecimal balance = BigDecimal.ZERO;
    // if (ECoinType.ORIGINAL.getCode().equals(coin.getType())) {
    // if (EOriginialCoin.ETH.getCode().equals(account.getSymbol())) {
    //
    // balance = EthClient.getBalance(account.getAddress());
    //
    // } else if (EOriginialCoin.BTC.getCode()
    // .equals(account.getSymbol())) {
    //
    // BtcAddressProperties btcAddressProperties = btcBlockExplorer
    // .getAddressProperties(account.getAddress());
    // balance = btcAddressProperties.getBalance();
    //
    // } else if (EOriginialCoin.WAN.getCode()
    // .equals(account.getSymbol())) {
    //
    // balance = WanClient.getBalance(account.getAddress());
    //
    // }
    // } else if (ECoinType.ETH_TOKEN.getCode().equals(coin.getType())) {
    //
    // balance = TokenClient.getBalance(account.getAddress(),
    // coin.getContractAddress());
    //
    // } else if (ECoinType.WAN_TOKEN.getCode().equals(coin.getType())) {
    //
    // balance = WanTokenClient.getBalance(account.getAddress(),
    // coin.getContractAddress());
    //
    // }
    // XN802270DetailRes newAccount = new XN802270DetailRes();
    // newAccount.setSymbol(account.getSymbol());
    // newAccount.setAddress(account.getAddress());
    // newAccount.setBalance(balance);
    // resultList.add(newAccount);
    //
    // }
    //
    // res.setAccountList(resultList);
    //
    // return res;
    // }

}
