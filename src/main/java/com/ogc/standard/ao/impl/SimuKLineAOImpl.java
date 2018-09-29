package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogc.standard.ao.ISimuKLineAO;
import com.ogc.standard.bo.IExchangePairBO;
import com.ogc.standard.bo.ISimuKLineBO;
import com.ogc.standard.bo.ISimuMatchResultHistoryBO;
import com.ogc.standard.bo.ISimuOrderDetailBO;
import com.ogc.standard.common.DateUtil;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.ExchangePair;
import com.ogc.standard.domain.SimuKLine;
import com.ogc.standard.domain.SimuMatchResultHistory;
import com.ogc.standard.domain.SimuOrderDetail;
import com.ogc.standard.enums.ESimuKLinePeriod;

@Service
public class SimuKLineAOImpl implements ISimuKLineAO {

    @Autowired
    private ISimuKLineBO simuKLineBO;

    @Autowired
    private IExchangePairBO exchangePairBO;

    @Autowired
    private ISimuOrderDetailBO simuOrderDetailBO;

    @Autowired
    private ISimuMatchResultHistoryBO simuMatchResultHistoryBO;

    @Override
    public List<SimuKLine> querySimuKLineList(String symbol, String toSymbol,
            String period) {

        SimuKLine condition = new SimuKLine();
        condition.setSymbol(symbol);
        condition.setToSymbol(toSymbol);
        condition.setPeriod(period);
        condition.setOrder("create_datetime asc");

        return simuKLineBO.querySimuKLineList(condition);
    }

    // 扫描平台支持的交易对
    public void saveKLine() {

        // 获取平台内的所有交易对
        List<ExchangePair> pairs = exchangePairBO
            .queryExchangePairList(new ExchangePair());

        for (ExchangePair pair : pairs) {

            saveKLineByPeriod(pair, ESimuKLinePeriod.MIN1.getCode());
            // 生成其他时间单位的K线
            doSaveOtherDate(pair);

        }

    }

    private void doSaveOtherDate(ExchangePair pair) {

        int min = getMinute();

        if (min % 5 == 0) {
            saveKLineByPeriod(pair, ESimuKLinePeriod.MIN5.getCode());
        }

        if (min % 15 == 0) {
            saveKLineByPeriod(pair, ESimuKLinePeriod.MIN15.getCode());
        }

        if (min % 30 == 0) {
            saveKLineByPeriod(pair, ESimuKLinePeriod.MIN30.getCode());
        }

        if (min == 0) {
            saveKLineByPeriod(pair, ESimuKLinePeriod.MIN60.getCode());
        }

        String date = getTimeByMinute(-1);

        if (date.indexOf("00:00:00") != -1) {
            saveKLineByPeriod(pair, ESimuKLinePeriod.DAY.getCode());

            // 星期
            if (getWeek(date) == 7) {
                saveKLineByPeriod(pair, ESimuKLinePeriod.WEEK.getCode());
            }
        }

        if (date.indexOf("01 00:00:00") != -1) {
            saveKLineByPeriod(pair, ESimuKLinePeriod.MONTH.getCode());
        }

        if (date.indexOf("01-01 00:00:00") != -1) {
            saveKLineByPeriod(pair, ESimuKLinePeriod.YEAR.getCode());
        }

    }

    public void saveKLineByPeriod(ExchangePair pair, String period) {

        // 验证历史上是否有交易产生,没有则不添加K线
        if (SimuOrderDetailValidater(pair)) {
            return;
        }

        ESimuKLinePeriod linePeriod = ESimuKLinePeriod
            .getESimuKLinePeriod(period);

        int unitMin = StringValidater.toInteger(linePeriod.getValue());

        List<SimuMatchResultHistory> resultHistorieList = getResultHistorieList(
            pair, unitMin);

        createKLine(pair, period, resultHistorieList);
    }

    private void createKLine(ExchangePair pair, String period,
            List<SimuMatchResultHistory> resultHistorieList) {

        // 成交量
        BigDecimal volume = BigDecimal.ZERO;
        // 成交笔数
        BigDecimal quantity = new BigDecimal(resultHistorieList.size() + "");
        // 成交额
        BigDecimal amount = BigDecimal.ZERO;
        // 开盘价（当前成交单的第一单）
        BigDecimal open = BigDecimal.ZERO;
        // 收盘价（当前成交单的最后一单）
        BigDecimal close = BigDecimal.ZERO;
        // 最高价
        BigDecimal high = BigDecimal.ZERO;
        // 最低价
        BigDecimal low = BigDecimal.ZERO;

        if (CollectionUtils.isNotEmpty(resultHistorieList)) {

            // 开盘价（当前成交单的第一单）
            open = resultHistorieList.get(0).getExchangeRate();

            // 收盘价（当前成交单的最后一单）
            close = resultHistorieList.get(resultHistorieList.size() - 1)
                .getExchangeRate();

            // 最低价从成交单里取一个默认值
            low = resultHistorieList.get(0).getExchangeRate();

            // 统计单位时间内的K线信息
            for (SimuMatchResultHistory resultHistory : resultHistorieList) {

                volume = volume.add(resultHistory.getSymbolCount());

                amount = amount.add(resultHistory.getToSymbolCount());

                if (high.compareTo(resultHistory.getExchangeRate()) < 0) {
                    high = resultHistory.getExchangeRate();
                }

                if (low.compareTo(resultHistory.getExchangeRate()) > 0) {
                    low = resultHistory.getExchangeRate();
                }

            }

        } else { // 当前时间单位没有交易产生

            // 获取上一时间单位的K线
            SimuKLine simuKLine = simuKLineBO.getLatestSimuKLine(
                pair.getSymbol(), pair.getToSymbol(),
                ESimuKLinePeriod.MIN1.getCode());

            if (null != simuKLine) {

                open = simuKLine.getClose();

                close = simuKLine.getClose();

            }

        }

        simuKLineBO.saveSimuKLine(pair, period, volume, quantity, amount, open,
            close, high, low);

    }

    private boolean SimuOrderDetailValidater(ExchangePair pair) {

        SimuOrderDetail condition = new SimuOrderDetail();
        condition.setSymbol(pair.getSymbol());
        condition.setToSymbol(pair.getToSymbol());

        List<SimuOrderDetail> simuOrderDetails = simuOrderDetailBO
            .querySimuOrderDetailList(condition);

        return CollectionUtils.isEmpty(simuOrderDetails);

    }

    /**
     * 根据交易对获取前一个时间单位起始到前一分钟结束的成交单
     * @param pair
     * @param LastMin
     * @return 
     * @create: 2018年9月4日 上午3:44:48 lei
     * @history:
     */
    private List<SimuMatchResultHistory> getResultHistorieList(
            ExchangePair pair, int unitMin) {
        List<SimuMatchResultHistory> resultHistorieList;

        SimuMatchResultHistory condition = new SimuMatchResultHistory();
        condition.setSymbol(pair.getSymbol());
        condition.setToSymbol(pair.getToSymbol());

        // 单位时间
        String lastMin = getTimeByMinute(unitMin);
        condition.setCreateDatetimeStart(
            DateUtil.strToDate(lastMin + ":00", DateUtil.DATA_TIME_PATTERN_1));

        // 上一分钟的59秒
        condition.setCreateDatetimeEnd(DateUtil.strToDate(
            getTimeByMinute(-1) + ":59", DateUtil.DATA_TIME_PATTERN_1));
        condition.setOrder("create_datetime asc");

        resultHistorieList = simuMatchResultHistoryBO
            .querySimuMatchResultHistoryList(condition);

        return resultHistorieList;
    }

    // 根据分钟数获取时间
    public String getTimeByMinute(int minute) {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MINUTE, minute);

        return new SimpleDateFormat("yyyy-MM-dd HH:mm")
            .format(calendar.getTime());

    }

    // 获取上一分钟的分钟数
    public int getMinute() {

        int min = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -1);
        min = Integer
            .parseInt(new SimpleDateFormat("mm").format(calendar.getTime()));

        return min;

    }

    // 获取星期数
    public static int getWeek(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

}
