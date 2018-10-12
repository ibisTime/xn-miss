/**
 * @Title Status.java 
 * @Package com.ibis.pz.enums 
 * @Description 
 * @author miyb  
 * @date 2015-3-7 上午8:41:50 
 * @version V1.0   
 */
package com.ogc.standard.enums;

import java.util.HashMap;
import java.util.Map;

import com.ogc.standard.common.SysConstants;

/** 
 * 中文文本
 * @author: taojian 
 * @since: 2018年9月20日 下午7:44:25 
 * @history:
 */
public enum EErrorCode_main {

    // DEFAULT("Biz000", "系统错误"),

    // 用户
    user_MOBILEEXIST("000001", "手机号已存在"),

    user_LOGINNAME("000002", "登录名不存在"),

    user_LOGINPWD("000003", "登陆密码错误"),

    user_ACCABNOMAL("000004", "账号状态不正常"),

    user_SAMEMOBILE("000005", "新手机与原手机号码一致"),

    user_SAMELOGINPWD("000006", "新登录密码不能与原有密码重复"),

    user_DOIDFIRST("000007", "请先实名认证"),

    user_IDWRONG("000008", "身份证不符合"),

    user_TRADEPWDWRONG("000009", "旧资金密码不正确"),

    user_SAMETRADEPWD("000010", "新支付密码与原有支付密码重复"),

    user_LOGOUT("000011", "管理员无法注销"),

    user_HAVEBOUNDMOBILE("000012", "用户已绑定手机"),

    user_HAVEBOUNDEMAIL("000013", "用户已绑定邮箱"),

    user_ISNOTQDS("000014", "用户不是渠道商"),

    user_EMAILEXIST("000015", "邮箱已经存在"),

    user_NICKNAMEEXIST("000016", "昵称已存在"),

    user_MOBILE("000017", "手机号[%s]用户不存在"),

    user_USERIDUNEXIST("000018", "用户编号%s不存在"),

    user_TRADEPWDFIRST("000019", "请您先设置支付密码"),

    user_TRADEPWDISWRONG("000020", "支付密码错误"),

    user_OLDLOGINPWDWRONG("000021", "原登录密码错误"),

    // 数字货币
    coin_ADDRESSNOTEXIST("000022", "不存在该地址"),

    coin_INVALIDATE("000023", "地址已失效，无需重复弃用"),

    coin_UNSUPPORT("000024", "暂不支持此类型的币种"),

    coin_WADDRESSEXIST("000025", "归集地址%s已经存在，请勿重复导入"),

    coin_ADDRESSRULE("000026", "地址%s不符合规则，请仔细核对"),

    coin_SYMBOLUSED("000027", "币种符号%s已经被使用"),

    coin_CONTARTADDRESS("000028", "合约地址%s已经被使用"),

    coin_COINPUBLISHED("000029", "币种已发布，请勿重复操作"),

    coin_COINUNPUBLISH("000030", "币种未发布，不能进行撤下操作"),

    // 承兑商
    accept_UNSUPPORT("0000031", "付款方式不支持"),

    accept_SALEABLE("000032", "可出售余额不足"),

    accept_UNCANCEL("000033", "当前状态下不能处理"),

    accept_OUTOFTIME("000034", "订单支付超时，系统自动取消"),

    accept_ONLYBUYER("000035", "只有此单的买家才可以标记打款"),

    accept_STATUSPAY("000036", "当前状态下不能标记已打款"),

    accept_ORDEREXIST("000037", "订单不存在"),

    accept_PRICEZERO("000038", "交易价格必须大于0"),

    accept_PRICEERROR("000039", "交易价格误差过大"),

    accept_AMOUNTZERO("000038", "交易金额必须大于0"),

    accept_COUNTZERO("000039", "交易数量必须大于0"),

    // 账户
    account_EXIST("000040", "账户不存在"),

    account_TRANSFERAMOUNT("000041", "转账金额需大于0"),

    account_PERSONALLEFT("000042", "个人账户可用余额不足"),

    account_NOTEXIST("000170", "用户[%s;%s]无此类型账户"),

    account_UNFROZENZERO("000171", "本次解冻会使账户冻结金额小于0"),

    account_SAME("000043", "来去双方账号一致，无需内部划转"),

    // 广告
    ads_PUBLISHTYPE("000044", "发布类型不支持"),

    ads_STATUSSUPPORT("000045", "当前状态不支持该操作"),

    ads_YOUSELF("000046", "只有本人能操作"),

    ads_FAILED("000047", "操作失败"),

    ads_ID("000048", "请传入广告编号"),

    ads_SELLCOUNT("000049", "出售总量必须大于0"),

    ads_MINTRADE("000050", "单笔最小交易额必须大于0"),

    ads_MAXTRADE("000051", "单笔最大交易额必须大于0"),

    ads_MAXMIN("000052", "单笔最大交易额需大于等于单笔最小交易额"),

    ads_COUNTMIN("000053", "发布总量价值需大于等于单笔最小交易额"),

    ads_FEEFROZEN("000054", "由于要冻结相应广告费，您最多可以出售%s"),

    ads_EXIST("000055", "原广告不存在"),

    ads_CHANGEAMOUNT("000056", "改变金额需大于0"),

    ads_ISEXIST("000057", "您已经有一个已上架的购买广告"),

    // 仲裁工单
    arb_STAUTUS("000058", "仲裁工单不处于未处理状态"),

    arb_NOTEXIST("000059", "仲裁工单不存在"),

    // 奖励
    awa_HANDLE("000060", "奖励已处理"),

    awa_NOTEXIST("000061", "不存在该奖励"),

    awa_MONTHSETTLE("000062", "不能结算本月的奖励"),

    // 银行卡
    ban_BANKCARD("000063", "银行卡编号不存在"),

    ban_ISEXIST("000064", "银行卡号已存在"),

    // 黑民单
    bla_NOTEXIST("000065", "黑名单记录不存在"),

    bla_INBLACKLIST("000066", "该用户已经在黑名单中"),

    // btc
    btc_BROADCAST("000067", "广播记录不存在"),

    // 渠道银行
    chann_NOTEXIST("000068", "渠道银行不存在"),

    // 充值
    charge_AMOUNT("000069", "充值金额需大于零"),

    charge_STATUS("000070", "申请记录状态不是待支付状态，无法支付"),

    charge_NOTEXIST("000071", "订单不存在"),

    // 属性
    code_NOTEXIST("000072", "编号不存在"),

    coin_ONESYMBOL("000073", "至少保留一个币种"),

    // 归集地址
    coll_UNABLE("000074", "该地址不能归集"),

    coll_CONDITION("000075", "未达到归集条件，无需归集"),

    coll_LEFTCOUNT("000076", "余额不足以支付矿工费，不能归集"),

    coll_FAILED("000078", "交易广播失败"),

    // 评论
    comm_KEYWORD("000079", "评论内容存在关键字：【%s】,请删除后再评论！"),

    comm_STATUS("000080", "评论不处于可以进行此操作的状态！"),

    comm_USERRIGHTS("000081", "当前用户没有权限删除该评论！"),

    comm_NOTEXIST("000082", "评论记录不存在"),

    // 汇率
    curr_NOTEXIST("000083", "传入货币类型不存在"),

    curr_GETERROR("000084", "汇率获取异常"),

    id_NOTEXIST("000085", "id不存在"),

    // eth
    eth_COLLECT("000086", "该地址不能归集"),

    eth_NOTCOLLECT("000087", "未达到归集条件，无需归集"),

    eth_RECORDNOTEXIST("000088", "以太坊交易记录不存在"),

    eth_GSEPRICEERROR("000089", "以太坊gas价格获取异常"),

    eth_KEYSTOREERROE("000090", "keystore文件写入异常，原因%s"),

    eth_BROADCAST("000091", "交易广播异常 %s"),

    eth_COLLECTNOTFOUND("000092", "未找到可用的归集地址"),

    // google
    goog_FAILED("000093", "谷歌验证码校验失败，请仔细核对！"),

    // groupcoinjour
    groupjourcoin_NOTEMPTY("000094", "新增流水流水分组不能为空"),

    groupjourcoin_NOTZERO("000095", "新增流水变动金额不能为0"),

    // 流水
    jour_DUIZHANGSTATUS("000096", "该流水不处于待对账状态"),

    // 关键字
    keyword_EXIST("000097", "关键字记录已存在,请更换关键字"),

    keyword_NOTEXIST("000098", "关键字记录不存在"),

    market_RATE("000099", "行情加权值获取异常"),

    personal_REPEATEDLY("000100", "请勿重复添加地址"),

    signlog_TODAYSIGNED("000101", "今日已签到"),

    // 币币交易
    simuorder_UNSUPPORTDIR("000102", "不支持的买卖方向"),

    simuorder_UNSUPPORTREVORK("000103", "当前状态下不支持撤单"),

    simuorder_PRICE("000104", "限价委托单委托价格需必填"),

    simucount_COUNT("000105", "委托数量应在%s和%s之间"),

    simuorder_MINCOUNT("000106", "委托数量应当" + SysConstants.minCountLimit + "的整数倍"),

    simuorder_PRICELESSTHAN("000107", "委托价格不得高于前收盘价的900%"),

    simuorder_PRICEMORETHAN("000108", "委托价格不得低于前收盘价测50%"),

    // 消息
    sms_PUBLISHED("000109", "消息已发布！"),

    sms_NOTEXIST("000110", "消息不存在！"),

    smsout_EMAILUSED("000111", "邮箱已经被使用"),

    // 数据字典
    dict_SECONDPARENTKEY("000112", "第二层字典数据，parentKey不能为空"),

    dict_PARENTKEYNOTEXIST("000113", "parentKey不存在"),

    dict_KEYREPEAT("000114", "当前节点下，key不能为重复"),

    code_EXIST("000115", "编号已存在"),

    // 系统用户
    sysuser_CLOSE("000116", "管理员无法注销"),

    page_STARTENDTIME("000117", "开始时间不能大于结束时间"),

    sysuser_NOTEXIST("000118", "系统用户不存在"),

    // 订单
    tradeorder_UNDONE("000119", "您有尚未完成的购买订单"),

    tradeorder_NOTSELL("000120", "该广告不是卖币广告"),

    tradeorder_UNSELL("000121", "您有尚未完成的出售订单"),

    tradeorder_NOTBUY("000122", "该广告不是购买广告"),

    tradeOrder_TRUST("000123", "您未被广告发布者信任，不能与之进行交易"),

    tradeorder_BLACKLIST("000124", "您已被该用户拉黑，不能与他进行交易"),

    tradeorder_ADS("000125", "广告未上架，不能进行交易"),

    tradeorder_YOUCANNOT("000126", "您是广告发布者，不能进行该操作"),

    tradeorder_STATUSCANCEL("000127", "当前状态下不能取消订单"),

    tradeorder_CANNOTMARK("000128", "当前状态下不能标记已打款"),

    tradeorder_CANNOTRELEASE("000129", "当前状态下不能释放"),

    tradeorder_NOCOMMENT("000130", "您无权评价该交易订单"),

    tradeorder_NOARBITRATE("000131", "当前状态下不能申请仲裁"),

    tradeorder_USERNOTARB("000132", "您无权申请仲裁"),

    tradeorder_CANNOTDELETE("000133", "当前状态订单无法删除"),

    tradeorder_COMMENTREPEAT("000134", "您已经完成评价，请勿重复评价"),

    tradeorder_MINPRICE("000135", "交易金额未达最低限额"),

    tradeorder_MAXPRICE("000136", "交易金额超过最高限额"),

    date_WRONGFORMAT("000137", "日期格式不正确"),

    user_APPROVE("000138", "不能修改手机号邮箱以外的信息"),

    user_INAPPROVE("000139", "该用户有申请在审批中"),

    idauth_SUCCESSED("000140", "该用户已认证成功"),

    idauth_APPROVED("000141", "该申请已审批"),

    idauth_INAPPROVE("000142", "该用户已有认证审批中"),

    relatin_ONESELF("000143", "用户不能和自己建立关系"),

    relation_EXIST("000144", "用户关系已建立"),

    relation_CANCEL("000145", "解除原关系失败"),

    relation_NONETOCANCEL("000146", "用户关系未建立，无法解除"),

    setting_UNSUPPORTSET("000147", "不支持的设置类型"),

    setting_BEADDED("000148", "您已经添加过该设置"),

    setting_FAILCANCEL("000149", "取消设置失败"),

    setting_FAILADD("000150", "添加设置失败"),

    with_COUNTFEE("000151", "提现金额需大于手续费"),

    with_APPROVE("000152", "申请记录状态不是待审批状态，无法审批"),

    with_BROADCAST("000153", "前取现记录不处于待广播状态"),

    with_NOTEMPTY("000154", "散取地址不能为空"),

    eth_INVALIBLEADDRESS("000155", "无效的ETH地址，只有散取地址才能进行取现广播！"),

    eth_INWITHDRAW("000156", "该散取地址正在广播使用，请稍后再试！"),

    eth_ADDRESSABUNDON("000157", "该散取地址已被弃用！"),

    with_INVILEDADDRESS("000158", "无效的取现地址"),

    with_FAILED("000159", "提现广播失败"),

    with_SIGNED("000160", "交易签名失败，请仔细检查散取地址是否符合提现要求"),

    with_STATUS("000161", "申请记录状态不是待支付状态，无法支付"),

    with_AGAIN("000162", "上笔取现申请还未处理成功，不能再次申请"),

    with_ZERO("000163", "取现金额不能为0"),

    address_RULE("000164", "地址%s不符合%s规则，请仔细核对"),

    config_TYPE("000165", "type类型不在枚举类中 0-第一层 1-第二层"),

    hl_ZERO("000166", "红蓝订单的变动金额不能为0"),

    mobile_ILEAGLE("000167", "手机号格式非法"),

    user_LOGINNAMERE("000168", "登录名重复"),

    user_NOTEXIST("000169", "用户不存在"),

    mobile_UNBIND("000172", "请先绑定手机号"),

    simuorder_PRICE_0("000173", "限价委托单委托价格需大于0");

    EErrorCode_main(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static Map<String, EErrorCode_main> getMap() {
        Map<String, EErrorCode_main> map = new HashMap<String, EErrorCode_main>();
        for (EErrorCode_main eLanguage_zh_CN : EErrorCode_main.values()) {
            map.put(eLanguage_zh_CN.getCode(), eLanguage_zh_CN);
        }
        return map;
    }

    private String code;

    private String value;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
