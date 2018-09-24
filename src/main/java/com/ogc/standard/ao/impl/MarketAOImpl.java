package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ogc.standard.ao.IMarketAO;
import com.ogc.standard.bo.ICoinBO;
import com.ogc.standard.bo.ICurrencyRateBO;
import com.ogc.standard.bo.IMarketBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.common.OkHttpUtils;
import com.ogc.standard.common.SysConstants;
import com.ogc.standard.domain.Coin;
import com.ogc.standard.domain.Market;
import com.ogc.standard.domain.MarketDetail;
import com.ogc.standard.dto.req.XN650101Req;
import com.ogc.standard.enums.ECoin;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.enums.EErrorCode_main;
import com.ogc.standard.enums.EMarketOrigin;
import com.ogc.standard.exception.BizException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/** 
 * 
 * @author: lei 
 * @since: 2018年8月22日 下午8:15:15 
 * @history:
 */
@Service
public class MarketAOImpl implements IMarketAO {

    private static final Logger logger = LoggerFactory
        .getLogger(MarketAOImpl.class);

    private static List<MarketDetail> marketDetailList;

    @Autowired
    private IMarketBO marketBO;

    @Autowired
    ICurrencyRateBO currencyRateBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    ICoinBO coinBO;

    @Override
    public List<Market> marketListByReq(XN650101Req req) {

        Market condition = new Market();
        condition.setSymbol(req.getSymbol());
        condition.setReferCurrency(req.getReferCurrency());
        return this.marketBO.marketListByCondation(condition);

    }

    // 获取多个币种的行情列表
    @Scheduled(cron = "*/30 * * * * ?")
    public void obtainMarketDetailList() {

        // 去拉取行情列表
        String requestStr = "https://api.coinmarketcap.com/v1/ticker/?limit=2";
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().get().url(requestStr).build();
        Call call = okHttpClient.newCall(request);
        try {

            Response response = call.execute();
            String jsonStr = response.body().string();

            // 先转化为map
            List<Map> marketDetailMapList = JSONArray.parseArray(jsonStr,
                Map.class);

            //
            List<com.ogc.standard.domain.MarketDetail> marketDetailList = JSONArray
                .parseArray(jsonStr, MarketDetail.class);

            if (marketDetailMapList != null) {

                BigDecimal currencyRate = this.currencyRateBO
                    .currencyRateByCurrency(ECurrency.USD.getCode()).getRate();
                for (int i = 0; i < marketDetailList.size(); i++) {

                    MarketDetail marketDetail = marketDetailList.get(i);
                    Map marketDetailMap = marketDetailMapList.get(i);

                    BigDecimal priceCNY = new BigDecimal(
                        marketDetail.getPrice_usd()).multiply(currencyRate);
                    String priceCNYString = priceCNY
                        .setScale(2, RoundingMode.HALF_EVEN).toString();
                    marketDetail.setPrice_cny(priceCNYString);

                    marketDetail.setOne_day_volume_usd(
                        (String) marketDetailMap.get("24h_volume_usd"));

                    // 获取人民币转化
                    BigDecimal one_day_volume_cny = new BigDecimal(
                        marketDetail.getOne_day_volume_usd())
                            .multiply(currencyRate);
                    String one_day_volume_cny_String = one_day_volume_cny
                        .setScale(2, RoundingMode.HALF_EVEN).toString();
                    marketDetail
                        .setOne_day_volume_cny(one_day_volume_cny_String);
                }

                MarketAOImpl.marketDetailList = marketDetailList;

            } else {

                // logger.error(requestStr
                // + "行情详情数据拉取异常"
                // + Thread.currentThread().getStackTrace()[1]
                // .getMethodName());

            }

        } catch (Exception e) {

            // logger
            // .error("行情详情数据拉取异常，原因："
            // + e.getMessage()
            // + Thread.currentThread().getStackTrace()[1]
            // .getMethodName());

        }

    }

    public void obtainMarket() {

        // 获取平台支持的币种列表
        List<Coin> openCoinList = coinBO.queryOpenCoinList();

        // 从交易所获取币种
        String requestStr = "https://api.coinmarketcap.com/v2/listings/";
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().get().url(requestStr).build();
        Call call = okHttpClient.newCall(request);

        try {

            Response response = call.execute();
            String jsonStr = response.body().string();
            // 先转化为map
            Map jsonMap = (Map) JSON.parseObject(jsonStr, HashMap.class);

            List<Map> dataList = (List<Map>) jsonMap.get("data");

            for (Map coinInfo : dataList) {

                for (Coin coin : openCoinList) {

                    if (String.valueOf(coinInfo.get("symbol"))
                        .equals(coin.getSymbol())) {
                        // 从coinmarketcap获取

                        this.obtainCoinMarketCap(
                            String.valueOf(coinInfo.get("symbol")),
                            String.valueOf(coinInfo.get("id")));
                    }

                }

            }

        } catch (Exception e) {

            logger.error("行情详情数据拉取异常，原因：" + e.getMessage()
                    + Thread.currentThread().getStackTrace()[1]
                        .getMethodName());

        }

    }

    private void obtainCoinMarketCap(String coin, String coinId) {

        String requestStr = "https://api.coinmarketcap.com/v2/ticker/" + coinId
                + "/?convert=CNY";

        String jsonStr = OkHttpUtils.doAccessHTTPGetJson(requestStr);

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);

        // 落地Coin的CNY行情
        JSONObject cnyObject = jsonObject.getJSONObject("data")
            .getJSONObject("quotes").getJSONObject("CNY");
        saveMarket(coin, "coinmarketcap", ECurrency.CNY.getCode(), coinId,
            cnyObject.getBigDecimal("price"));

        // 落地Coin的USD行情
        JSONObject usdObject = jsonObject.getJSONObject("data")
            .getJSONObject("quotes").getJSONObject("USD");
        saveMarket(coin, "coinmarketcap", ECurrency.USD.getCode(), coinId,
            usdObject.getBigDecimal("price"));

    }

    @Override
    public void saveMarket(String coin, String origin, String currency,
            String coinId, BigDecimal price) {
        Market cnyMarket = new Market();
        cnyMarket.setReferCurrency(currency);
        cnyMarket.setSymbol(coin);
        cnyMarket.setOrigin(origin);
        cnyMarket.setUpdateDatetime(new Date());
        cnyMarket.setLastPrice(price);

        cnyMarket.setCoinmarketcapId(coinId);
        cnyMarket.setMid(price);
        cnyMarket.setLow(BigDecimal.ZERO);
        cnyMarket.setHigh(BigDecimal.ZERO);

        // 确定mid
        // 保存 存在就更新，不存在就插入
        this.marketBO.updateMarket(EMarketOrigin.COINMARKETCAP.getCode(), coin,
            cnyMarket);

    }

    @Override
    public Market coinPriceByPlatform(String coin, String refCurrency) {

        // 获取平台调控值
        BigDecimal x = BigDecimal.ZERO;

        ECoin eCoin = null;
        if (coin.equals(ECoin.ETH.getCode())) {
            eCoin = ECoin.ETH;
            x = this.sysConfigBO
                .getBigDecimalValue(SysConstants.ETH_COIN_PRICE_X);
        } else if (coin.equals(ECoin.X.getCode())) {
            eCoin = ECoin.X;
            x = this.sysConfigBO
                .getBigDecimalValue(SysConstants.X_COIN_PRICE_X);
        } else if (coin.equals(ECoin.BTC.getCode())) {
            eCoin = ECoin.BTC;
            x = this.sysConfigBO
                .getBigDecimalValue(SysConstants.BTC_COIN_PRICE_X);
        }

        if (eCoin == null) {
            throw new BizException(EErrorCode_main.coin_UNSUPPORT.getCode());
        }

        Market market = this.marketBO.standardMarket(eCoin, refCurrency);

        // 计算平台调控过的值
        market.setMid(market.getMid().add(x));
        return market;

    }

}
