// package com.ogc.standard.match;
//
// import java.math.BigDecimal;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;
//
// import org.apache.commons.collections.CollectionUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
//
// import com.ogc.standard.bo.ICoinBO;
// import com.ogc.standard.bo.IHandicapBO;
// import com.ogc.standard.bo.ISYSConfigBO;
// import com.ogc.standard.bo.ISimuMatchResultBO;
// import com.ogc.standard.bo.ISimuOrderBO;
// import com.ogc.standard.bo.ISimuOrderDetailBO;
// import com.ogc.standard.bo.ISimuOrderHistoryBO;
// import com.ogc.standard.bo.IUserBO;
// import com.ogc.standard.common.CoinUtil;
// import com.ogc.standard.common.SysConstants;
// import com.ogc.standard.core.StringValidater;
// import com.ogc.standard.domain.Coin;
// import com.ogc.standard.domain.Handicap;
// import com.ogc.standard.domain.HandicapGrade;
// import com.ogc.standard.domain.SimuOrder;
// import com.ogc.standard.domain.SimuOrderDetail;
// import com.ogc.standard.domain.User;
// import com.ogc.standard.enums.EHandicapQuantity;
// import com.ogc.standard.enums.ESimuOrderDirection;
// import com.ogc.standard.enums.ESimuOrderStatus;
// import com.ogc.standard.enums.ESimuOrderType;
//
/// **
// * 币币交易撮合类
// * @author: lei
// * @since: 2018年8月31日 下午4:50:02
// * @history:
// */
// @Service
// public class SimuOrderMatch {
//
// @Autowired
// private ISimuOrderBO simuOrderBO;
//
// @Autowired
// private ISimuOrderDetailBO simuOrderDetailBO;
//
// @Autowired
// private ISimuOrderHistoryBO simuOrderHistoryBO;
//
// @Autowired
// private IHandicapBO handicapBO;
//
// @Autowired
// private ISimuMatchResultBO simuMatchResultBO;
//
// @Autowired
// private IUserBO userBO;
//
// @Autowired
// private ISYSConfigBO sysConfigBO;
//
// @Autowired
// private ICoinBO coinBO;
//
// /**
// * 按交易对查找委托单进行撮合逻辑
// * 1、市价单先撮合
// * 2、先查对方五档，if没有，补充，if 补充没有return；
// * 3、执行撮合逻辑，重建盘口
// */
// public void doMatchTrade(String symbol, String toSymbol) {
//
// List<SimuOrder> simuOrders = queryMarketSimuOrderList(symbol, toSymbol);
// if (CollectionUtils.isNotEmpty(simuOrders)) {
//
// // 存在市价单时，优先撮合市价单
// matchMarket(simuOrders, symbol, toSymbol);
//
// } else {
//
// // 不存在市价单时，撮合限价单
// matchLimit(symbol, toSymbol);
//
// }
//
// }
//
// public void match(SimuOrder simuOrder) {
//
// if (ESimuOrderType.MARKET.getCode().equals(simuOrder.getType())) {
// doMatchMarket(simuOrder);
// } else {
// doMatchLimit(simuOrder);
// }
//
// }
//
// /**
// * 从存活委托单池内获取委托单
// * @param symbol
// * @param toSymbol
// * @return
// * @create: 2018年8月31日 下午5:13:50 lei
// * @history:
// */
// private List<SimuOrder> queryMarketSimuOrderList(String symbol,
// String toSymbol) {
// List<String> statusList = new ArrayList<>();
// statusList.add(ESimuOrderStatus.SUBMIT.getCode());
// statusList.add(ESimuOrderStatus.PART_DEAL.getCode());
//
// SimuOrder condition = new SimuOrder();
// condition.setSymbol(symbol);
// condition.setToSymbol(toSymbol);
// condition.setStatusList(statusList);
// condition.setType(ESimuOrderType.MARKET.getCode());
// condition.setOrder("create_datetime asc");
//
// return simuOrderBO.querySimuOrderList(condition);
// }
//
// /**
// * 市价单撮合
// * 1.从存活委托单池内取出对应交易对下所有市价单
// * 2.获取对向盘口档位，循环盘口档位，根据价格匹配市价单
// * 3.撮合当前档位下匹配到的市价单和盘口
// * 4.修改市价单、盘口交易信息
// * 5.根据最后的撮合结果修改市价单信息
// */
// private void matchMarket(List<SimuOrder> simuOrders, String symbol,
// String toSymbol) {
//
// for (SimuOrder marketOrder : simuOrders) {
//
// // 获取对向盘口档位
// List<HandicapGrade> handicapGrades = queryHandicapGrades(
// marketOrder.getSymbol(), marketOrder.getToSymbol(),
// marketOrder.getDirection());
//
// if (CollectionUtils.isEmpty(handicapGrades)) {
// //
// doMarchMarketDone(marketOrder);
// }
//
// boolean isHGBreak = false; // 盘口档位循环是否可以停止
// for (HandicapGrade handicapGrade : handicapGrades) {
//
// for (Handicap handicap : handicapGrade.getHandicapList()) {
//
// // 根据盘口获取委托单
// SimuOrder limitOrder = simuOrderBO
// .getSimuOrder(handicap.getOrderCode());
//
// if (null == limitOrder) {
// break;
// }
//
// // 双委托单所有者为同一人
// if (marketOrder.getUserId()
// .equals(limitOrder.getUserId())) {
// continue;
// }
//
// // 委托总量减去已成交量：委托剩余可交易量
// BigDecimal avilAmount = marketOrder.getTotalAmount()
// .subtract(marketOrder.getTradedAmount());
//
// if (avilAmount.compareTo(BigDecimal.ZERO) == 0) {
// isHGBreak = true;
// break;
// }
//
// doMatchMarket(marketOrder, limitOrder, avilAmount);
//
// }
//
// if (isHGBreak) {
// break;
// }
//
// }
//
// /** 当前市价委托单已 撮合 完所有的对方盘口 **/
// doMarchMarketDone(marketOrder);
//
// }
//
// // 全部市价单成交完毕后撮合 限价单
//
// // doMatchLimit(symbol, toSymbol);
// }
//
// /**
// * 限价单撮合（循环优化for替换为map）
// * 1.获取买卖双方盘口档位，以买盘为主，用卖盘来进行撮合
// * 2.匹配双方盘口
// * 3.撮合当前档位下匹配到的双方盘口
// * 4.修改双方盘口、委托单交易信息
// */
// private void matchLimit(String symbol, String toSymbol) {
//
// // 获取买盘盘口档位
// List<HandicapGrade> bidsGrades = queryHandicapGrades(symbol, toSymbol,
// ESimuOrderDirection.SELL.getCode());
//
// // 每次循环都获取卖盘盘口档位
// List<HandicapGrade> asksGrades = queryHandicapGrades(symbol, toSymbol,
// ESimuOrderDirection.BUY.getCode());
//
// // 用买盘 去扫 卖盘
// for (HandicapGrade bidsGrade : bidsGrades) {
//
// for (Handicap bidsHandicap : bidsGrade.getHandicapList()) {
//
// for (HandicapGrade asksGrade : asksGrades) {
//
// for (Handicap asksHandicap : asksGrade.getHandicapList()) {
//
// // 根据盘口获取委托单
// SimuOrder bidsOrder = simuOrderBO
// .getSimuOrder(bidsHandicap.getOrderCode());
//
// if (null == bidsOrder) {
// break;
// }
//
// // 委托总量减去已成交量：剩余可交易量
// BigDecimal avilBidsAmount = bidsOrder.getTotalAmount()
// .subtract(bidsOrder.getTradedAmount());
//
// if (avilBidsAmount.compareTo(BigDecimal.ZERO) == 0) {
// break;
// }
//
// // 根据盘口获取委托单
// SimuOrder asksOrder = simuOrderBO
// .getSimuOrder(asksHandicap.getOrderCode());
//
// if (null == asksOrder) {
// break;
// }
//
// // 双委托单所有者为同一人
// if (bidsOrder.getUserId()
// .equals(asksOrder.getUserId())) {
// continue;
// }
//
// // 卖盘盘口价格 低于等于 买盘盘口价格 撮合成功
// if (asksOrder.getPrice()
// .compareTo(bidsOrder.getPrice()) <= 0) {
//
// doMatchLimit(bidsOrder, asksOrder);
//
// }
//
// }
//
// }
//
// }
//
// }
//
// }
//
// //
// private void doMatchMarket(SimuOrder marketOrder, SimuOrder limitOrder,
// BigDecimal avilAmount) {
//
// // 获取盘口币种单位
// Coin asksCoin = coinBO.getCoin(limitOrder.getSymbol());
//
// // 盘口交易信息
// BigDecimal handicapPrice = limitOrder.getPrice();
// BigDecimal handicapCount = limitOrder.getTotalCount()
// .subtract(limitOrder.getTradedCount());
// BigDecimal handicapAmount = handicapPrice
// .multiply(CoinUtil.fromMinUnit(handicapCount, asksCoin.getUnit()));
//
// // 当前盘口的交易量是否能满足
// if (handicapAmount.compareTo(avilAmount) > 0) {
// // 计算交易数量
// BigDecimal tradeCount = avilAmount.divide(handicapPrice, 4,
// BigDecimal.ROUND_DOWN);
//
// // 当前委托单完全成交，新增当前委托单交易明细单
// SimuOrderDetail marketDetail = doOrderMatch(marketOrder,
// handicapPrice, tradeCount, avilAmount,
// ESimuOrderStatus.ENTIRE_DEAL.getCode());
//
// // 盘口部分成交，新增盘口单交易明细单
// SimuOrderDetail handicapDetail = doOrderMatch(limitOrder,
// handicapPrice, tradeCount, avilAmount,
// ESimuOrderStatus.PART_DEAL.getCode());
//
// // 落地撮合结果
// simuMatchResultBO.doSimuMatchResult(marketDetail, handicapDetail);
//
// } else if (handicapAmount.compareTo(avilAmount) == 0) {
// // 当前委托单完全成交，新增当前委托单交易明细单
// SimuOrderDetail marketDetail = doOrderMatch(marketOrder,
// handicapPrice, handicapCount, handicapAmount,
// ESimuOrderStatus.ENTIRE_DEAL.getCode());
//
// // 盘口完全成交，新增盘口单交易明细单
// SimuOrderDetail handicapDetail = doOrderMatch(limitOrder,
// handicapPrice, handicapCount, handicapAmount,
// ESimuOrderStatus.ENTIRE_DEAL.getCode());
//
// // 落地撮合结果
// simuMatchResultBO.doSimuMatchResult(marketDetail, handicapDetail);
//
// } else {
// // 当前委托单部分成交，新增当前委托单交易明细单
// SimuOrderDetail marketDetail = doOrderMatch(marketOrder,
// handicapPrice, handicapCount, handicapAmount,
// ESimuOrderStatus.PART_DEAL.getCode());
//
// // 盘口完全成交，新增盘口单交易明细单
// SimuOrderDetail handicapDetail = doOrderMatch(limitOrder,
// handicapPrice, handicapCount, handicapAmount,
// ESimuOrderStatus.ENTIRE_DEAL.getCode());
//
// // 落地撮合结果
// simuMatchResultBO.doSimuMatchResult(marketDetail, handicapDetail);
//
// }
//
// }
//
// private void doMatchLimit(SimuOrder bidsOrder, SimuOrder asksOrder) {
//
// // 获取买方币种单位
// Coin bidsCoin = coinBO.getCoin(bidsOrder.getSymbol());
//
// // 买单交易信息
// BigDecimal bidsPrice = bidsOrder.getPrice();
// BigDecimal bidsCount = bidsOrder.getTotalCount()
// .subtract(bidsOrder.getTradedCount());
// BigDecimal bidsAmount = bidsPrice
// .multiply(CoinUtil.fromMinUnit(bidsCount, bidsCoin.getUnit()));
//
// // 获取卖方币种单位
// Coin asksCoin = coinBO.getCoin(bidsOrder.getSymbol());
//
// // 卖单交易信息
// BigDecimal asksPrice = asksOrder.getPrice();
// BigDecimal asksCount = asksOrder.getTotalCount()
// .subtract(asksOrder.getTradedCount());
// BigDecimal asksAmount = asksPrice
// .multiply(CoinUtil.fromMinUnit(asksCount, asksCoin.getUnit()));
//
// // 买盘交易量能否被满足
// if (asksAmount.compareTo(bidsAmount) > 0) {
//
// // 买盘单完全成交
// SimuOrderDetail bidsDetail = doOrderMatch(bidsOrder, bidsPrice,
// bidsCount, bidsAmount, ESimuOrderStatus.ENTIRE_DEAL.getCode());
//
// // 卖盘单部分成交
// SimuOrderDetail asksDetail = doOrderMatch(asksOrder, bidsPrice,
// bidsCount, bidsAmount, ESimuOrderStatus.PART_DEAL.getCode());
//
// // 落地撮合结果
// simuMatchResultBO.doSimuMatchResult(bidsDetail, asksDetail);
//
// } else if (asksAmount.compareTo(bidsAmount) == 0) {
//
// // 买盘单完全成交
// SimuOrderDetail bidsDetail = doOrderMatch(bidsOrder, bidsPrice,
// bidsCount, bidsAmount, ESimuOrderStatus.ENTIRE_DEAL.getCode());
//
// // 卖盘单完全成交
// SimuOrderDetail asksDetail = doOrderMatch(asksOrder, asksPrice,
// asksCount, asksAmount, ESimuOrderStatus.ENTIRE_DEAL.getCode());
//
// // 落地撮合结果
// simuMatchResultBO.doSimuMatchResult(bidsDetail, asksDetail);
//
// } else {
//
// // 买盘单部分成交
// SimuOrderDetail bidsDetail = doOrderMatch(bidsOrder, asksPrice,
// asksCount, asksAmount, ESimuOrderStatus.PART_DEAL.getCode());
//
// // 卖盘单完全成交
// SimuOrderDetail asksDetail = doOrderMatch(asksOrder, asksPrice,
// asksCount, asksAmount, ESimuOrderStatus.ENTIRE_DEAL.getCode());
//
// // 落地撮合结果
// simuMatchResultBO.doSimuMatchResult(bidsDetail, asksDetail);
//
// }
// }
//
// /**
// * 市价单撮合结束后处理
// * @param marketOrder
// * @create: 2018年9月8日 下午1:54:31 lei
// * @history:
// */
// private void doMarchMarketDone(SimuOrder marketOrder) {
// // 获取当前市价委托单的所有成交明细
// SimuOrderDetail condition = new SimuOrderDetail();
// condition.setOrderCode(marketOrder.getCode());
// List<SimuOrderDetail> simuOrderDetails = simuOrderDetailBO
// .querySimuOrderDetailList(condition);
//
// if (CollectionUtils.isEmpty(simuOrderDetails)) {
//
// // 此委托单无法成交
// marketOrder.setAvgPrice(BigDecimal.ZERO);
// marketOrder.setTradedCount(BigDecimal.ZERO);
// marketOrder.setTradedAmount(BigDecimal.ZERO);
// marketOrder.setStatus(ESimuOrderStatus.CANCEL.getCode());
// marketOrder.setCancelDatetime(new Date());
//
// } else {
//
// // 比较所有明细交易金额相加 和 当前委托单的委托金额，小于则为部分成交，等于则为全部成交
// BigDecimal totalTradeAmount = BigDecimal.ZERO;
//
// // 成交总数量
// BigDecimal totalTradeCount = BigDecimal.ZERO;
//
// for (SimuOrderDetail simuOrderDetail : simuOrderDetails) {
//
// totalTradeAmount = totalTradeAmount
// .add(simuOrderDetail.getTradedAmount());
//
// totalTradeCount = totalTradeCount
// .add(simuOrderDetail.getTradedCount());
//
// }
//
// // 计算成交均价
// BigDecimal avgPrice = totalTradeAmount.divide(totalTradeCount, 4,
// BigDecimal.ROUND_DOWN);
//
// // 装填委托单信息并转入历史委托单
// marketOrder.setAvgPrice(avgPrice);
// marketOrder.setTradedCount(totalTradeCount);
// marketOrder.setTradedAmount(totalTradeAmount);
//
// }
//
// moveToHistory(marketOrder);
// }
//
// private List<HandicapGrade> queryHandicapGrades(String symbol,
// String toSymbol, String direction) {
//
// // 把当前买卖方向 转换为 对向买卖方向，再去补充盘口
// if (ESimuOrderDirection.BUY.getCode().equals(direction)) {
// direction = ESimuOrderDirection.SELL.getCode();
// } else {
// direction = ESimuOrderDirection.BUY.getCode();
// }
//
// // 补充盘口
// handicapBO.stuffHandicap(symbol, toSymbol, direction,
// StringValidater.toInteger(EHandicapQuantity.FIFTY.getCode()));
//
// // 获取盘口档位
// List<HandicapGrade> handicapGrades = handicapBO
// .queryHandicapGradeList(symbol, toSymbol, direction);
//
// return handicapGrades;
// }
//
// /**
// * 处理委托单撮合结果
// * @param simuOrder
// * @param handicap
// * @param tradedCount
// * @param tradedAmount
// * @create: 2018年8月31日 下午5:12:47 lei
// * @history:
// */
// private SimuOrderDetail doOrderMatch(SimuOrder simuOrder,
// BigDecimal tradedPrice, BigDecimal tradedCount,
// BigDecimal tradedAmount, String status) {
//
// // 新增成交单并更新委托单
// BigDecimal tradedFee = getFee(simuOrder.getUserId(),
// simuOrder.getDirection(), tradedCount, tradedAmount);
// SimuOrderDetail orderDetail = simuOrderDetailBO.saveSimuOrderDetail(
// simuOrder, tradedPrice, tradedCount, tradedAmount, tradedFee);
//
// // 更新委托单交易信息
// updateSimuOrderTradeInfo(simuOrder, tradedCount, tradedAmount, status);
//
// // 委托单是限价单时更新盘口信息
// if (ESimuOrderType.LIMIT.getCode().equals(simuOrder.getType())) {
// // 更新盘口交易信息
// updateHandicapTradeInfo(simuOrder.getCode(),
// simuOrder.getTotalCount().subtract(simuOrder.getTradedCount()),
// status);
// }
//
// return orderDetail;
// }
//
// /**
// * 更新委托单交易信息
// * @param simuOrder
// * @param tradedCount
// * @param tradedAmount
// * @param status
// * @create: 2018年8月31日 下午5:08:32 lei
// * @history:
// */
// private void updateSimuOrderTradeInfo(SimuOrder simuOrder,
// BigDecimal tradedCount, BigDecimal tradedAmount, String status) {
//
// simuOrder.setAvgPrice(simuOrder.getPrice());
// simuOrder.setLastTradedDatetime(new Date());
// simuOrder.setStatus(status);
//
// if (ESimuOrderStatus.PART_DEAL.getCode().equals(status)) { // 部分成交
//
// simuOrder
// .setTradedCount(simuOrder.getTradedCount().add(tradedCount));
// simuOrder
// .setTradedAmount(simuOrder.getTradedAmount().add(tradedAmount));
// // 交易手续费
// simuOrder.setTradedFee(
// simuOrder.getTradedFee().add(getFee(simuOrder.getUserId(),
// simuOrder.getDirection(), tradedCount, tradedAmount)));
//
// simuOrderBO.refreshLimitSimuOrder(simuOrder);
//
// } else if (ESimuOrderStatus.ENTIRE_DEAL.getCode().equals(status)) { // 完全成交
//
// simuOrder.setTradedCount(tradedCount);
// simuOrder.setTradedAmount(tradedAmount);
// // 交易手续费
// simuOrder.setTradedFee(getFee(simuOrder.getUserId(),
// simuOrder.getDirection(), tradedCount, tradedAmount));
//
// // 放入历史委托单
// moveToHistory(simuOrder);
// }
//
// }
//
// /**
// * 根据委托单更新盘口交易信息
// * @param orderCode
// * @param tradedCount
// * @param status
// * @create: 2018年8月31日 下午5:08:00 lei
// * @history:
// */
// private void updateHandicapTradeInfo(String orderCode,
// BigDecimal tradedCount, String status) {
//
// Handicap condition = new Handicap();
// condition.setOrderCode(orderCode);
// condition.setCount(tradedCount);
//
// if (ESimuOrderStatus.PART_DEAL.getCode().equals(status)) { // 部分成交
//
// // 更新盘口可交易量
// handicapBO.refreshHandicap(condition);
//
// } else if (ESimuOrderStatus.ENTIRE_DEAL.getCode().equals(status)) { // 完全成交
//
// // 删除盘口
// handicapBO.removeHandicap(condition.getOrderCode());
// }
//
// }
//
// /**
// * 将存活委托单移入历史委托单
// * @param data
// * @create: 2018年8月31日 下午5:06:44 lei
// * @history:
// */
// private void moveToHistory(SimuOrder data) {
// // 增加历史委托
// simuOrderHistoryBO.saveSimuOrderHistory(data);
//
// // 撤销，并从 存活委托 中删除；
// simuOrderBO.cancel(data);
// }
//
// /**
// * 计算手续费
// * @param tradeAmount
// * @return
// * @create: 2018年9月4日 下午8:16:58 lei
// * @history:
// */
// private BigDecimal getFee(String userId, String direction,
// BigDecimal symbolAmount, BigDecimal toSymbolAmount) {
//
// User user = userBO.getUser(userId);
//
// BigDecimal rate = sysConfigBO
// .getBigDecimalValue(SysConstants.SIMU_ORDER_FEE_RATE);
//
// BigDecimal fee = BigDecimal.ZERO;
// // 手续费收取：买单收取symbol，卖单收取toSymbol(得到什么币收取什么币)
// if (ESimuOrderDirection.BUY.getCode().equals(direction)) {
// fee = symbolAmount.multiply(rate);
// } else {
// fee = toSymbolAmount.multiply(rate);
// }
//
// return fee;
// }
//
// }
