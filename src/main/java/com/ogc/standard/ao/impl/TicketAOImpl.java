package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.ITicketAO;
import com.ogc.standard.ao.IWeChatAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IActionBO;
import com.ogc.standard.bo.IPlayerBO;
import com.ogc.standard.bo.IRankBO;
import com.ogc.standard.bo.ISYSConfigBO;
import com.ogc.standard.bo.ITicketBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.common.SysConstant;
import com.ogc.standard.core.StringValidater;
import com.ogc.standard.domain.Player;
import com.ogc.standard.domain.Rank;
import com.ogc.standard.domain.SYSConfig;
import com.ogc.standard.domain.Ticket;
import com.ogc.standard.domain.User;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.dto.res.XN002501Res;
import com.ogc.standard.enums.EActionToType;
import com.ogc.standard.enums.EActionType;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.enums.EJourBizTypeBusiness;
import com.ogc.standard.enums.EJourBizTypePlat;
import com.ogc.standard.enums.EJourBizTypeUser;
import com.ogc.standard.enums.EPayType;
import com.ogc.standard.enums.EPlayerStatus;
import com.ogc.standard.enums.ERankType;
import com.ogc.standard.enums.ESysUser;
import com.ogc.standard.enums.ETicketStatus;
import com.ogc.standard.exception.BizException;
import com.ogc.standard.exception.EBizErrorCode;

@Service
public class TicketAOImpl implements ITicketAO {

    @Autowired
    private ITicketBO ticketBO;

    @Autowired
    private IPlayerBO playerBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IRankBO rankBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IWeChatAO weChatAO;

    @Autowired
    private IActionBO actionBO;

    @Override
    public String orderTicket(String playerCode, Long ticket, String applyUser) {
        Player player = playerBO.getPlayer(playerCode);
        if (!EPlayerStatus.UP.getCode().equals(player.getStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "该选手不可以加油");
        }

        BigDecimal price = StringValidater.toBigDecimal(sysConfigBO
            .getConfigValue(SysConstant.PRICE).getCvalue());

        Integer invalidTime = StringValidater.toInteger(sysConfigBO
            .getConfigValue(SysConstant.INVALID_TIME).getCvalue());

        return ticketBO.saveTicket(player, ticket, applyUser, price,
            invalidTime);
    }

    @Override
    public void cancelTicket(String code, String userId, String remark) {
        Ticket data = ticketBO.getTicket(code);
        if (!ETicketStatus.TO_PAY.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "订单不是待支付状态，不能取消");
        }
        if (!userId.equals(data.getApplyUser())) {
            throw new BizException("xn0000", "订单编号：" + code + "不是您本人的订单，不能取消");
        }
        ticketBO.refreshCancelTicket(data, remark);
    }

    @Override
    @Transactional
    public Object toPayTicket(String code, String payType, String tradePwd) {
        Ticket data = ticketBO.getTicketForUpdate(code);
        if (StringUtils.isNotBlank(tradePwd)) {
            userBO.checkTradePwd(data.getApplyUser(), tradePwd);
        }
        if (!ETicketStatus.TO_PAY.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "订单不是待支付状态，不能支付");
        }

        // 支付订单
        Object result = null;
        if (EPayType.RMB_YE.getCode().equals(payType)) {// 余额支付
            result = toPayTicketYue(data, tradePwd);
        } else if (EPayType.WEIXIN_H5.getCode().equals(payType)) {// 微信支付
            result = toPayTicketWeChat(data);
        } else {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "支付方式不支持");
        }
        return result;
    }

    // 1、C端账户转账给平台账户
    // 2、更新订单
    // 3、更新选手票数和排名
    private Object toPayTicketYue(Ticket data, String tradePwd) {
        // 验证交易密码
        userBO.checkTradePwd(data.getApplyUser(), tradePwd);

        Player player = playerBO.getPlayer(data.getPlayerCode());

        // 更新业务订单（加油订单）
        ticketBO.payYueSuccess(data);

        // 人民币余额划转
        BigDecimal payAmount = data.getAmount();
        accountBO.transAmountCZB(data.getApplyUser(), ECurrency.CNY.getCode(),
            ESysUser.SYS_USER.getCode(), ECurrency.CNY.getCode(), payAmount,
            EJourBizTypeUser.TICKET.getCode(),
            EJourBizTypePlat.TICKET.getCode(),
            EJourBizTypeUser.TICKET.getValue(),
            EJourBizTypePlat.TICKET.getValue(), data.getCode());

        // 给品牌方加油分成
        SYSConfig rate = sysConfigBO
            .getConfigValue(SysConstant.DIVIDEND_RATE_BUSINESS);
        BigDecimal individeAmount = data.getAmount()
            .multiply(new BigDecimal(rate.getCvalue()))
            .setScale(0, BigDecimal.ROUND_DOWN);
        accountBO.transAmountCZB(ESysUser.SYS_USER.getCode(),
            ECurrency.CNY.getCode(), ESysUser.B_USER.getCode(),
            ECurrency.CNY.getCode(), individeAmount,
            EJourBizTypePlat.AJ_JYFC.getCode(),
            EJourBizTypeBusiness.AJ_JYFC.getCode(),
            EJourBizTypePlat.AJ_JYFC.getValue(),
            EJourBizTypeBusiness.AJ_JYFC.getValue(), data.getCode());

        // 更新选手票数
        playerBO.addPlayerTicket(player, data.getTicket());

        // 加油后默认关注选手
        actionBO.saveAction(EActionType.ATTENTION.getCode(),
            EActionToType.PLAYER.getCode(), data.getPlayerCode(),
            data.getApplyUser(), "");
        return new BooleanRes(true);
    }

    // 更新榜单
    @Override
    @Transactional
    public void upgradeRank(String orderCode) {
        Ticket ticket = ticketBO.getTicket(orderCode);
        Player player = playerBO.getPlayer(ticket.getPlayerCode());
        // 更新日版榜排名
        Rank rankDay = rankBO.getRankByPlayerCodeAndType(player.getCode(),
            ERankType.DAY.getCode());
        if (null == rankDay) {
            rankDay = rankBO.saveRank(player, ERankType.DAY.getCode(), ticket);
        }
        rankBO.refreshRanking(ERankType.DAY.getCode(), rankDay);

        // 更新总版榜排名
        Rank rankTotal = rankBO.getRankByPlayerCodeAndType(player.getCode(),
            ERankType.TOTAL.getCode());
        if (null == rankTotal) {
            rankTotal = rankBO.saveRank(player, ERankType.TOTAL.getCode(),
                ticket);
        }
        rankBO.refreshRanking(ERankType.TOTAL.getCode(), rankTotal);
    }

    private Object toPayTicketWeChat(Ticket data) {
        User user = userBO.getUser(data.getApplyUser());
        String payGroup = ticketBO.addPayGroup(data,
            EPayType.WEIXIN_H5.getCode());
        XN002501Res prepayIdH5 = weChatAO.getPrepayIdH5(data.getApplyUser(),
            user.getH5OpenId(), ESysUser.SYS_USER.getCode(), payGroup,
            data.getCode(), EJourBizTypeUser.AJ_CZ.getCode(),
            EJourBizTypeUser.AJ_CZ.getValue(), data.getAmount());
        return prepayIdH5;
    }

    @Override
    @Transactional
    public void paySuccess(String payGroup, String payCode) {
        Ticket data = ticketBO.getTicketForUpdate(payGroup);// payGroup==code
        if (!ETicketStatus.TO_PAY.getCode().equals(data.getStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "该订单不是待支付");
        }

        // 给品牌方加油分成
        SYSConfig rate = sysConfigBO
            .getConfigValue(SysConstant.DIVIDEND_RATE_BUSINESS);
        BigDecimal individeAmount = data.getAmount()
            .multiply(new BigDecimal(rate.getCvalue()))
            .setScale(0, BigDecimal.ROUND_DOWN);
        accountBO.transAmountCZB(ESysUser.SYS_USER.getCode(),
            ECurrency.CNY.getCode(), ESysUser.B_USER.getCode(),
            ECurrency.CNY.getCode(), individeAmount,
            EJourBizTypePlat.AJ_JYFC.getCode(),
            EJourBizTypeBusiness.AJ_JYFC.getCode(),
            EJourBizTypePlat.AJ_JYFC.getValue(),
            EJourBizTypeBusiness.AJ_JYFC.getValue(), data.getCode());

        // 更新选手票数
        Player player = playerBO.getPlayerForUpdate(data.getPlayerCode());
        playerBO.addPlayerTicket(player, data.getTicket());

        // 加油后默认关注选手
        actionBO.saveAction(EActionType.ATTENTION.getCode(),
            EActionToType.PLAYER.getCode(), data.getPlayerCode(),
            data.getApplyUser());
    }

    @Override
    public Paginable<Ticket> queryTicketPage(int start, int limit,
            Ticket condition) {
        Paginable<Ticket> paginable = ticketBO.getPaginable(start, limit,
            condition);
        List<Ticket> list = paginable.getList();
        for (Ticket ticket : list) {
            init(ticket);
        }
        return paginable;
    }

    @Override
    public List<Ticket> queryTicketList(Ticket condition) {
        List<Ticket> list = ticketBO.queryTicketList(condition);
        for (Ticket ticket : list) {
            init(ticket);
        }
        return list;
    }

    @Override
    public Ticket getTicket(String code) {
        return ticketBO.getTicket(code);
    }

    public void init(Ticket data) {
        if (StringUtils.isNotBlank(data.getPlayerCode())) {
            Player player = playerBO.getPlayer(data.getPlayerCode());
            data.setPlayerInfo(player);
        }
        if (StringUtils.isNotBlank(data.getApplyUser())) {
            User applyUserInfo = userBO.getUser(data.getApplyUser());
            data.setApplyUserInfo(applyUserInfo);

        }
    }
}
