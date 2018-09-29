package com.ogc.standard.common;

import java.math.BigDecimal;

public class SysConstants {

    public static final String admin = "admin";

    public static final String ACTIVITY_TRADE_FEE_RATE = "activity_trade_fee_rate"; // 活动期间交易广告费

    public static final String COLLECTION_LIMIT = "collection_limit"; // 账户余额大于等于该值时，进行归集

    public static final String WITHDRAW_FEE_ETH = "withdraw_fee_eth"; // ETH提现手续费

    public static final String WITHDRAW_FEE_BTC = "withdraw_fee_btc"; // BTC提现手续费

    public static final String ETH_COIN_PRICE_X = "eth_coin_price_x"; // ETH价格计算因子

    public static final String BTC_COIN_PRICE_X = "btc_coin_price_x"; // BTC价格计算因子

    public static final String X_COIN_PRICE_X = "X_COIN_PRICE_X"; // X价格计算因子

    public static final String CUSER_REG = "cuser_reg"; // 普通用户推荐送X币

    public static final String DUSER_REG = "duser_reg"; // 渠道商推荐送X币

    public static final String REFEREE_CUSER_FEE_RATE = "referee_cuser_fee_rate"; // 普通用户推荐用户交易分成

    public static final String REFEREE_DUSER_FEE_RATE = "referee_duser_fee_rate"; // 渠道商推荐用户交易分成

    // 七牛云图片配置

    public static String QINIU_ACCESS_KEY = "qiniu_access_key";

    public static String QINIU_SECRET_KEY = "qiniu_secret_key";

    public static String QINIU_BUCKET = "qiniu_bucket";

    public static String QINIU_DOMAIN = "qiniu_domain";

    // 腾讯云配置
    public static String TX_APP_CODE = "tx_app_code";// 应用编号

    public static String TX_APP_ADMIN = "tx_app_admin";// 应用编号

    public static String TX_ACCESS_KEY = "tx_access_key";// 公钥

    public static String TX_SECRET_KEY = "tx_secret_key";// 私钥

    public static String TX_ACCOUNT_TYPE = "tx_account_type";// 账号类型

    // 取现规则配置
    public static String CUSERQXBS = "CUSERQXBS"; // C端用户取现倍数

    public static String CUSERQXFL = "CUSERQXFL"; // C端用户取现费率

    public static String CUSERQXSX = "CUSERQXSX"; // C端用户取现时效

    public static String CUSERMONTIMES = "CUSERMONTIMES"; // C端用户每月取现次数

    public static String TRANSAMOUNTBS = "TRANSAMOUNTBS"; // C端2C端转账金额倍数

    public static String QXDBZDJE = "QXDBZDJE"; // 取现单笔最大金额

    // 短信通知配置
    public static String ZC_SMS_NOTICE = "zc_sms_notice"; // 仲裁短信通知

    public static String QX_SMS_NOTICE = "qx_sms_notice";// 取现短信通知

    // ****** 短信模板 ******

    // 有人仲裁
    public static String ARBITRATE = "您有一个新的仲裁订单（编号：%s）需要处理！";

    // 有人下单
    public static String ORDER_SUBMIT = "您发布的广告已经有新用户下单了噢，赶紧去处理吧！";

    // 有人开始聊天
    public static String ORDER_CONTACT = "您的广告刚刚被新用户关注，可能随时跟您聊天，赶紧去看看吧！";

    // 有人提现通知运营人员
    public static String WITHDRAW_CN = "您有一个新的取现订单（编号：%s），请及时处理！";

    // 代注册
    public static String DO_ADD_USER_CN = "尊敬的%s用户，您已成功注册，您的默认登录密码是%s，请及时登录平台修改。";

    // 修改手机号
    public static String DO_CHANGE_MOBILE_CN = "尊敬的%s用户，您于%s提交的更改绑定手机号码服务已完成，现绑定手机号码为%s，请妥善保管您的账户相关信息。";

    // 修改登录密码
    public static String DO_MODIFY_LOGIN_PWD_CN = "尊敬的%s用户，您的登录密码修改成功。请妥善保管您的账户相关信息。";

    // 重置登录密码
    public static String DO_RESET_LOGIN_PWD_CN = "尊敬的%s用户，您的登录密码重置成功。请妥善保管您的账户相关信息。";

    // 修改资金密码
    public static String DO_MODIFY_TRADE_PWD_CN = "尊敬的%s用户，您的资金密码修改成功。请妥善保管您的账户相关信息。";

    // 重置资金密码
    public static String DO_RESET_TRADE_PWD_CN = "尊敬的%s用户，您的资金密码重置成功。请妥善保管您的账户相关信息。";

    // 封禁用户
    public static String DO_LOCK_USER_CN = "尊敬的%s用户，您的账号已被管理员封禁，请遵守平台相关规则。";

    // 解禁用户
    public static String DO_UNLOCK_USER_CN = "尊敬的%s用户，您的账号已被管理员解封，请遵守平台相关规则。";

    // 绑定手机
    public static String DO_BIND_MOBILE_CN = "尊敬的%s用户，您于%s提交的绑定手机号码服务已完成，现绑定手机号码为%s，请妥善保管您的账户相关信息。";

    // ****** OTC ******
    public static final String ACCEPT_ORDER_LIMIT_TIME = "accept_order_pay_limit_time"; // 承兑商支付超时时间

    public static final String ACCEPT_ORDER_BUY_FEE_RATE = "accept_order_buy_fee_rate"; // 买入交易手续费

    public static final String ACCEPT_ORDER_SELL_FEE_RATE = "accept_order_sell_fee_rate";// 卖出交易手续费

    public static final String ACCEPT_ORDER_CANCEL_MAX_TIME = "accept_order_cancel_max_time";// 每天交易取消最大次数

    public static final String ACCEPT_ORDER_MIN_AMOUNT = "accept_order_min_amount";// 单笔交易最小额度(人民币)

    public static final String ACCEPT_ORDER_MAX_AMOUNT = "accept_order_max_amount";// 单笔交易最大额度(人民币)

    public static final String TRADE_FEE_RATE = "trade_fee_rate"; // 用户交易广告费率

    // ****** 币币交易 ******

    public static final String SIMU_ORDER_FEE_RATE = "simu_order_fee_rate"; // 用户币币交易手续费率

    public static Integer handicapLimit = 7; // 盘口数量限制

    public static BigDecimal minCountLimit = new BigDecimal("0.00001"); // 最小委托数量

    public static BigDecimal maxCountLimit = new BigDecimal("1000000"); // 最大委托数量

    // 提币手续费率
    public static String WITHDRAW_FEE = "withdraw_fee"; // 用户提币手续费率
}
