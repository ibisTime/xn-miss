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

    user_EMAILEXIST("000015", "邮箱已经存在"),

    user_NICKNAMEEXIST("000016", "昵称已存在"),

    user_MOBILE("000017", "手机号[%s]用户不存在"),

    user_USERIDUNEXIST("000018", "用户编号%s不存在"),

    user_TRADEPWDFIRST("000019", "请您先设置支付密码"),

    user_TRADEPWDISWRONG("000020", "支付密码错误"),

    user_OLDLOGINPWDWRONG("000021", "原登录密码错误"),

    USER_STATUS_NOT_NORMAL("000022", "用户状态异常"),

    signlog_TODAYSIGNED("000030", "用户今日已签到"),

    // 账户
    account_EXIST("000040", "账户不存在"),

    account_TRANSFERAMOUNT("000041", "转账金额需大于0"),

    account_PERSONALLEFT("000042", "个人账户可用余额不足"),

    account_NOTEXIST("000170", "用户[%s;%s]无此类型账户"),

    account_UNFROZENZERO("000171", "本次解冻会使账户冻结金额小于0"),

    account_SAME("000043", "来去双方账号一致，无需内部划转"),

    // 银行卡
    ban_BANKCARD("000063", "银行卡编号不存在"),

    ban_ISEXIST("000064", "银行卡号已存在"),

    // 黑民单
    bla_NOTEXIST("000065", "黑名单记录不存在"),

    bla_INBLACKLIST("000066", "该用户已经在黑名单中"),

    // 渠道银行
    chann_NOTEXIST("000068", "渠道银行不存在"),

    // 充值
    charge_AMOUNT("000069", "充值金额需大于零"),

    charge_STATUS("000070", "申请记录状态不是待支付状态，无法支付"),

    charge_NOTEXIST("000071", "订单不存在"),

    // 属性
    code_NOTEXIST("000072", "编号不存在"),

    // 属性
    id_NOTEXIST("000072", "序号不存在"),

    // 评论
    comm_KEYWORD("000079", "评论内容存在关键字：【%s】,请删除后再评论！"),

    comm_STATUS("000080", "评论不处于可以进行此操作的状态！"),

    comm_USERRIGHTS("000081", "当前用户没有权限删除该评论！"),

    comm_NOTEXIST("000082", "评论记录不存在"),

    // 流水
    jour_DUIZHANGSTATUS("000096", "该流水不处于待对账状态"),

    // 关键字
    keyword_EXIST("000097", "关键字记录已存在,请更换关键字"),

    keyword_NOTEXIST("000098", "关键字记录不存在"),

    // 数据字典
    dict_SECONDPARENTKEY("000112", "第二层字典数据，parentKey不能为空"),

    dict_PARENTKEYNOTEXIST("000113", "parentKey不存在"),

    dict_KEYREPEAT("000114", "当前节点下，key不能为重复"),

    code_EXIST("000115", "编号已存在"),

    // 系统用户
    sysuser_CLOSE("000116", "管理员无法注销"),

    page_STARTENDTIME("000117", "开始时间不能大于结束时间"),

    sysuser_NOTEXIST("000118", "系统用户不存在"),

    date_WRONGFORMAT("000137", "日期格式不正确"),

    user_APPROVE("000138", "不能修改手机号邮箱以外的信息"),

    user_INAPPROVE("000139", "该用户有申请在审批中"),

    with_COUNTFEE("000151", "提现金额需大于手续费"),

    with_APPROVE("000152", "申请记录状态不是待审批状态，无法审批"),

    with_STATUS("000161", "申请记录状态不是待支付状态，无法支付"),

    with_AGAIN("000162", "上笔取现申请还未处理成功，不能再次申请"),

    with_ZERO("000163", "取现金额不能为0"),

    config_TYPE("000165", "type类型不在枚举类中 0-第一层 1-第二层"),

    hl_ZERO("000166", "红蓝订单的变动金额不能为0"),

    mobile_ILEAGLE("000167", "手机号格式非法"),

    user_LOGINNAMERE("000168", "登录名重复"),

    user_NOTEXIST("000169", "用户不存在"),

    mobile_UNBIND("000172", "请先绑定手机号");

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
