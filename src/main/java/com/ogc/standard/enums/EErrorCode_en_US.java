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
 * 英文文本
 * @author: taojian 
 * @since: 2018年9月20日 下午7:43:58 
 * @history:
 */
public enum EErrorCode_en_US {
    // 1
    user_MOBILEEXIST("000001", "Mobile is exist"),

    user_LOGINNAME("000002", "Login name is not exist"),

    user_LOGINPWD("000003", "Login password is wrong"),

    user_ACCABNOMAL("000004", "Account status is abnormal"),

    user_SAMEMOBILE("000005",
            "The new phone number is the same as the original phone number"),

    user_SAMELOGINPWD("000006",
            "The new login password cannot be duplicated with the original password"),

    user_DOIDFIRST("000007", "Please Verified"),

    user_IDWRONG("000008", "ID card does not match"),

    user_TRADEPWDWRONG("000009", "Old trade password is incorrect"),

    user_SAMETRADEPWD("000010",
            "The new trade password is the same as the original trade password"),

    user_LOGOUT("000011", "Administrator cannot log out"),

    user_HAVEBOUNDMOBILE("000012", "User has bound the phone"),

    user_HAVEBOUNDEMAIL("000013", "User has bound the phone"),

    user_ISNOTQDS("000014", "User is not a channel provider"),

    user_EMAILEXIST("000015", "Email is exist"),

    user_NICKNAMEEXIST("000016", "Nickname is exist"),

    user_MOBILE("000017", "The mobile [%s] is not exist"),

    user_USERIDUNEXIST("000018", "UserId:%s is not exist"),

    user_TRADEPWDFIRST("000019", "Please set the trade password first"),

    user_TRADEPWDISWRONG("000020", "Trade password is wrong"),

    user_OLDLOGINPWDWRONG("000021", "The old login password is wrong"),

    // 数字货币
    coin_ADDRESSNOTEXIST("000022", "This address is not exist"),

    coin_INVALIDATE("000023",
            "The address has expired and does not need to be deduplicated"),

    coin_UNSUPPORT("000024",
            "This type of currency is not supported temporarily"),

    coin_WADDRESSEXIST("000025",
            "The collection address %s already exists. Do not import again"),

    coin_ADDRESSRULE("000026",
            "The address %s does not meet the rules, please check carefully"),

    coin_SYMBOLUSED("000027", "Coin symbol %s is already in use"),

    coin_CONTARTADDRESS("000028", "Contract address %s is already in use"),

    coin_COINPUBLISHED("000029",
            "Currency has been released, please do not repeat"),

    coin_COINUNPUBLISH("000030", "Currency not released, cannot be removed"),

    // 承兑商
    accept_UNSUPPORT("0000031", "Payment method is not supported"),

    accept_SALEABLE("000032", "Insufficient saleable balance"),

    accept_UNCANCEL("000033", "Cannot cancel order in current state"),

    accept_OUTOFTIME("000034",
            "Order payment timed out, the system automatically cancels"),

    accept_ONLYBUYER("000035", "Only buyers of this order can mark the payment"),

    accept_STATUSPAY("000036",
            "Cannot mark a paid amount in the current status"),

    accept_ORDEREXIST("000037", "Order is not exist"),

    accept_PRICEZERO("000038", "Trade price must be greater than 0"),

    accept_PRICEERROR("000039", "Trade price error is too large"),

    accept_AMOUNTZERO("000038", "Trade amount have to be larger than 0"),

    accept_COUNTZERO("000039", "Trade count have to be larger than 0"),

    // 账户
    account_EXIST("000040", "Account is not exist"),

    account_TRANSFERAMOUNT("000041", "Transfer amount must be larger than 0"),

    account_PERSONALLEFT("000042",
            "Insufficient balance available for personal account"),

    account_NOTEXIST("000041",
            "User [%s;%s] does not have this type of account"),

    account_UNFROZENZERO("000042",
            "This thawing will make the account freeze amount less than 0"),

    account_SAME(
            "000043",
            "The account number of both parties is the same, no internal transfer is required"),

    // 广告
    ads_PUBLISHTYPE("000044", "Release type is not supported"),

    ads_STATUSSUPPORT("000045",
            "The current state does not support this operation"),

    ads_YOUSELF("000046", "You can not operate"),

    ads_FAILED("000047", "Operate failed"),

    ads_ID("000048", "Please submit the advertisement's id"),

    ads_SELLCOUNT("000049", "Sell count should be more than 0"),

    ads_MINTRADE("000050", "Min trade count should be more than 0"),

    ads_MAXTRADE("000051", "Max trade count should be more than 0"),

    ads_MAXMIN("000052", "Max trade count should be more tjan 0"),

    ads_COUNTMIN("000053",
            "The count advertisement publish should be more than min count"),

    ads_FEEFROZEN("000054",
            "You can sell less than %s,because of the advertisement fee"),

    ads_EXIST("000055", "Advertisement is not exist"),

    ads_CHANGEAMOUNT("000056", "Change amount should be larger than 0"),

    ads_ISEXIST("000057", "You have an advertisement which has published"),

    // 仲裁工单
    arb_STAUTUS("000058", "Arbitrate order is not to handle"),

    arb_NOTEXIST("000059", "Arbitrate order is not exist"),

    // 奖励
    awa_HANDLE("000060", "Award has handled"),

    awa_NOTEXIST("000061", "Award is exist"),

    awa_MONTHSETTLE("000062", "You can't settle this month's award"),

    // 银行卡
    ban_BANKCARD("000063", "Bankcard is not exist"),

    ban_ISEXIST("000064", "Bankcard code is exist"),

    // 黑民单
    bla_NOTEXIST("000065", "This blacklist is not exist"),

    bla_INBLACKLIST("000066", "This user is in blacklist already"),

    btc_BROADCAST("000067", "Broadcast is not exist"),

    // 渠道银行
    chann_NOTEXIST("000068", "Channelbank is not exist"),

    // 充值
    charge_AMOUNT("000069", "Charge amount should be more than 0"),

    charge_STATUS("000070", "This apply is not to charge"),

    charge_NOTEXIST("000071", "Order is not exist"),

    // 属性
    code_NOTEXIST("000072", "code is not exist"),

    coll_UNABLE("000074", "address is not able to collect"),

    coll_CONDITION("000075",
            "The collection condition is not met, no need to collect"),

    coll_LEFTCOUNT("000076",
            "The balance is not enough to pay the miners’ fees and cannot be collected."),

    coll_FAILED("000078", "Transaction broadcast failed"),

    comm_KEYWORD("000079",
            "Comment content exists keyword: [%s], please delete and comment!"),

    comm_STATUS("000080", "Comments are not in a state that can do this!"),

    comm_USERRIGHTS("000081",
            "The current user does not have permission to delete the comment!"),

    comm_NOTEXIST("000082", "Comment is not exist"),

    // 汇率
    curr_NOTEXIST("000083", "The currency submitted is not exist"),

    curr_GETERROR("000084", "Abnormal exchange rate"),

    id_NOTEXIST("000085", "Id is not exist"),

    eth_COLLECT("000086", "This address cannot be collected"),

    eth_NOTCOLLECT("000087",
            "The collection condition is not met, no need to collect"),

    eth_RECORDNOTEXIST("000088", "Ethereum transaction record does not exist"),

    eth_GSEPRICEERROR("000089", "Ethereum gas price gets abnormal"),

    eth_KEYSTOREERROE("000090",
            "Keystore file is written abnormally, reason %s"),

    eth_BROADCAST("000091", "Transaction broadcast exception %s"),

    eth_COLLECTNOTFOUND("000092", "No available collection address found"),

    // google
    goog_FAILED("000093",
            "Google verification code verification failed, please check carefully!"),

    // groupcoinjour
    groupjourcoin_NOTEMPTY("000094", "New stream water group cannot be empty"),

    groupjourcoin_NOTZERO("000095", "The added flow change amount cannot be 0"),

    // 流水
    jour_DUIZHANGSTATUS("000096", "The flow is not in a reconciled state"),

    // 关键字
    keyword_EXIST("000097", "Keyword is exist，please change the keyword"),

    keyword_NOTEXIST("000098", "Keyword is not exist"),

    market_RATE("000099", "Quote weighted value gets abnormal"),

    personal_REPEATEDLY("000100", "Do not add addresses repeatedly"),

    signlog_TODAYSIGNED("000101", "Signed in today"),

    // 币币交易
    simuorder_UNSUPPORTDIR("000102", "Unsupported trading direction"),

    simuorder_UNSUPPORTREVORK("000103",
            "Cannot withdraw the order in the current state"),

    simuorder_PRICE("000104", "Limit order entrustment price is required"),

    simucount_COUNT("000105",
            "The number of delegates should be between %s and %s"),

    simuorder_MINCOUNT("000106", "The number of orders should be"
            + SysConstants.minCountLimit + "Integer multiple"),

    simuorder_PRICELESSTHAN(
            "000107",
            "The commission price must not be higher than 900% of the previous closing price"),

    simuorder_PRICEMORETHAN("000108",
            "The commission price must not be less than 50% of the previous closing price"),

    // 消息
    sms_PUBLISHED("000109", "The message has been released!"),

    sms_NOTEXIST("000110", "The message is not exist!"),

    smsout_EMAILUSED("000111", "This email is userd"),

    // 数据字典
    dict_SECONDPARENTKEY("000112",
            "Second level dictionary data, parentKey cannot be empty"),

    dict_PARENTKEYNOTEXIST("000113", "parentKey is not exist"),

    dict_KEYREPEAT("000114",
            "Under the current node, the key cannot be duplicated"),

    code_EXIST("000115", "Code is exist"),

    sysuser_CLOSE("000116", "Administrator cannot log out"),

    page_STARTENDTIME("000117", "Start time cannot be greater than end time"),

    sysuser_NOTEXIST("000118", "System user does not exist"),

    tradeorder_UNDONE("000119", "You have an uncompleted purchase order"),

    tradeorder_NOTSELL("000120", "This ad is not a coin advertisement"),

    tradeorder_UNSELL("000121", "You have an uncompleted sale order"),

    tradeorder_NOTBUY("000122", "This ad is not a purchase ad"),

    tradeOrder_TRUST("000123",
            "You are not trusted by the publisher and cannot be traded with"),

    tradeorder_BLACKLIST("000124",
            "You have been blacked out by this user and cannot be traded with him"),

    tradeorder_ADS("000125", "The ad is not on the shelf and cannot be traded"),

    tradeorder_YOUCANNOT("000126", "You are an advertiser and can't do this"),

    tradeorder_STATUSCANCEL("000127", "Cannot cancel order in current state"),

    tradeorder_CANNOTMARK("000128",
            "Cannot mark a paid amount in the current state"),

    tradeorder_CANNOTRELEASE("000129",
            "Cannot be released in the current state"),

    tradeorder_NOCOMMENT("000130",
            "You are not authorized to rate the trade order"),

    tradeorder_NOARBITRATE("000131",
            "Cannot apply for arbitration under current status"),

    tradeorder_USERNOTARB("000132",
            "You are not entitled to apply for arbitration"),

    tradeorder_CANNOTDELETE("000133", "Current status order cannot be deleted"),

    tradeorder_COMMENTREPEAT("000134",
            "You have completed the review, please do not repeat the review"),

    tradeorder_MINPRICE("000135",
            "The transaction amount does not reach the minimum limit"),

    tradeorder_MAXPRICE("000136", "Transaction amount exceeds maximum limit"),

    date_WRONGFORMAT("000137", "Date format is incorrect"),

    user_APPROVE("000138",
            "Cannot modify information other than the phone number mailbox"),

    user_INAPPROVE("000139", "The user has an application for approval"),

    idauth_SUCCESSED("000140", "The user has been authenticated successfully"),

    idauth_APPROVED("000141", "The application has been approved"),

    idauth_INAPPROVE("000142", "The user has been approved for certification"),

    relatin_ONESELF("000143",
            "Users cannot establish relationships with themselves"),

    relation_EXIST("000144", "User relationship has been established"),

    relation_CANCEL("000145", "Failure to cancel the original relationship"),

    relation_NONETOCANCEL("000146",
            "User relationship has not been established and cannot be released"),

    setting_UNSUPPORTSET("000147", "Unsupported setup type"),

    setting_BEADDED("000148", "You have already added this setting"),

    setting_FAILCANCEL("000149", "Cancel setup failed"),

    setting_FAILADD("000150", "Add settings failed"),

    with_COUNTFEE("000151",
            "The cash amount needs to be greater than the handling fee"),

    with_APPROVE(
            "000152",
            "The status of the application record is not pending approval and cannot be approved"),

    with_BROADCAST("000153",
            "The pre-acquisition record is not pending broadcast"),

    with_NOTEMPTY("000154", "Buffer address cannot be empty"),

    eth_INVALIBLEADDRESS("000155",
            "Invalid ETH address, only the address can be fetched!"),

    eth_INWITHDRAW("000156",
            "he address is being broadcasted. Please try again later!"),

    eth_ADDRESSABUNDON("000157", "This spam address has been deprecated!"),

    with_INVILEDADDRESS("000158", "Invalid cash withdrawal address"),

    with_FAILED("000159", "Cash withdrawal failed"),

    with_SIGNED(
            "000160",
            "The transaction signature failed. Please check carefully whether the address is eligible for withdrawal."),

    with_STATUS("000161",
            "The status of the application record is not pending and cannot be paid."),

    with_AGAIN(
            "000162",
            "The application for cash withdrawal has not been processed successfully and cannot be applied again."),

    with_ZERO("000163", "The amount of cash cannot be 0");

    EErrorCode_en_US(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static Map<String, EErrorCode_en_US> getMap() {
        Map<String, EErrorCode_en_US> map = new HashMap<String, EErrorCode_en_US>();
        for (EErrorCode_en_US eLanguage_en_US : EErrorCode_en_US.values()) {
            map.put(eLanguage_en_US.getCode(), eLanguage_en_US);
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
