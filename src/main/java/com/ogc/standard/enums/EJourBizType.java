package com.ogc.standard.enums;

import java.util.HashMap;
import java.util.Map;

import com.std.account.exception.BizException;

public enum EJourBizType {
    // 通用业务类型 每个系统的分布说明
    AJ_REG("01", "注册送积分"), AJ_SIGN("02", "每日签到"), AJ_REG_REF("RF", "推荐注册送积分"), AJ_CZ(
            "11", "充值"), AJ_QX("-11", "取现"), AJ_HCLB("HL", "红冲蓝补"), Transfer_CURRENCY(
            "201", "同币种的划转"), EXCHANGE_CURRENCY("200", "币种兑换"), Transfer_CURRENCY_C2C(
            "206", "C端用户间转账")

    // 各自系统特有的业务类型
    //
    , AJ_GW("-30", "购物"), AJ_GWTK("30", "购物退款"), AJ_QRSH("32", "确认收货，商户收钱"), AJ_GMZKQ(
            "-40", "购买折扣券"), ZH_O2O("-ZH1", "O2O支付"), ZH_STOCK("-ZH2", "分红权分红"), AJ_GMHZB(
            "-50", "购买汇赚宝"), AJ_GMHZBFC("51", "购买汇赚宝分成"), AJ_YYJL("52",
            "汇赚宝摇一摇奖励"), AJ_YYFC("53", "汇赚宝摇一摇分成"), AJ_YYFC_REF("54",
            "推荐人摇一摇分成"), AJ_FSDHB("60", "发送得红包"), AJ_LQHB("61", "领取红包"), AJ_DUOBAO(
            "-70", "参与小目标"), AJ_DUOBAO_PRIZE("71", "小目标中奖")

    // 管道
    , GD_MALL("GD_MALL", "积分商城消费"), GD_O2O("GD_O2O", "O2O店铺积分消费"), GD_WCDDSJF(
            "GD_WCDDSJF", "完成订单送积分")

    // 城市网
    , CSW_PAY("100", "城市网商品积分购买支付")

    // 菜狗
    , CG_O2O_CGB("90", "菜狗O2O菜狗币支付"), AJ_CGBSM("210", "采购币售卖"), CG_HB2CGB(
            "211", "嗨币兑换菜狗币"), CG_O2O_CGBFD("91", "菜狗O2O菜狗币返点人民币"), CG_O2O_RMB(
            "92", "菜狗O2O人民币支付"), CG_O2O_CGJF("93", "菜狗O2O积分支付"), CG_O2O_RMBFD(
            "95", "菜狗O2O人民币支付返点菜狗币"), CG_XNCZ_P("94", "菜狗充值专区用款"), CG_XNCZ_M(
            "-94", "菜狗充值专区退款"), CG_CGBGM("CG_CGBGM", "菜狗币购买")

    // 定制通
    , AJ_GWFK("GW", "购物付款"), AJ_TK("GWTK", "购物退款"), AJ_HHRFC("HHRFC", "合伙人分成"), AJ_LTSFC(
            "LTSFC", "量体师分成")

    // 姚橙
    , YC_O2O_RMB("YC_O2O_RMB", "姚橙O2O人民币支付"), YC_O2O_RMBFD("YC_O2O_RMBFD",
            "姚橙O2O人民币支付返橙券"), YC_O2O_CB("YC_O2O_CB", "姚橙O2O橙券支付"), YC_O2O_CBFD(
            "YC_O2O_CBFD", "姚橙O2O橙券支付返人民币"), YC_MALL("YC_MALL", "姚橙商城购物支付"), YC_MALL_BACK(
            "YC_MALL_BACK", "姚橙商城购物退款"), YC_XNCZ_P("YC_XNCZ_P", "姚橙充值专区支付"), YC_XNCZ_M(
            "YC_XNCZ_M", "姚橙充值专区退款"), YC_CBGM("YC_CBGM", "橙券购买"), YC_SCB(
            "YC_SCB", "扫描二维码送橙券")

    // 健康E购
    , JKEG_FW("JKEG_FW", "服务购买"), JKEG_FWTK("JKEG_FWTK", "服务购买退款"), JKEG_O2O_RMB(
            "JKEG_O2O_RMB", "店铺消费云币支付"), AJ_FT("JK03", "发帖送积分"), AJ_PL("JK04",
            "评论送积分"), AJ_DZ("JK05", "点赞加积分"), AJ_BDZ("JK06", "被点赞送积分"), AJ_BPL(
            "JK07", "被评论送积分"), JJ_STZ("10", "JK删除帖子"), JJ_SPL("JK11", "删除评论"), JJ_QXDZ(
            "JK12", "取消点赞")

    // 户外电商
    , AJ_RENT("R01", "租赁"), AJ_RENT_TK("R02", "租赁退款"), AJ_RENT_OVERDUE("R03",
            "逾期罚息"), AJ_RENT_JS("R04", "租赁结算"), AJ_GW_BACK("GWBACK", "购物返利"), AJ_GW_ONE_REF_BACK(
            "GWOFBACK", "购物推荐人返利"), TRAVELS_DS("TRDS", "游记打赏"),

    HD_BM("HDBM", "活动下单"), HD_TK("HDTK", "活动退款"), HD_MFFB("HD_MFFB", "免费活动成团奖励"), HD_SFFB(
            "HD_SFFB", "收费活动成团奖励"), HD_FK("HD_FK", "活动反馈奖励"), HD_MFFY(
            "HD_MFFY", "活动免费返佣"), HD_SFFY("HD_SFFY", "活动收费返佣"), HD_CTJL(
            "HD_CTJL", "活动成团奖励"), HD_CTFY("HD_CTFY", "活动成团费用"), ORDER_ONE_BACK(
            "41", "一级推客返点"), ORDER_TWO_BACK("42", "二级推客返点"), ORDER_LEAD_BACK(
            "43", "领队推客返点");

    public static EJourBizType getBizType(String code) {
        Map<String, EJourBizType> map = getBizTypeMap();
        EJourBizType result = map.get(code);
        if (result == null) {
            throw new BizException("XN0000", code + "对应的bizType不存在");
        }
        return result;
    }

    public static Map<String, EJourBizType> getBizTypeMap() {
        Map<String, EJourBizType> map = new HashMap<String, EJourBizType>();
        for (EJourBizType bizType : EJourBizType.values()) {
            map.put(bizType.getCode(), bizType);
        }
        return map;
    }

    EJourBizType(String code, String value) {
        this.code = code;
        this.value = value;
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
