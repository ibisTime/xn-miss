package com.ogc.standard.ao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogc.standard.ao.ITicketAO;
import com.ogc.standard.bo.IAccountBO;
import com.ogc.standard.bo.IPlayerBO;
import com.ogc.standard.bo.IRankBO;
import com.ogc.standard.bo.ITicketBO;
import com.ogc.standard.bo.IUserBO;
import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Account;
import com.ogc.standard.domain.Player;
import com.ogc.standard.domain.Rank;
import com.ogc.standard.domain.Ticket;
import com.ogc.standard.dto.res.BooleanRes;
import com.ogc.standard.enums.ECurrency;
import com.ogc.standard.enums.EPayType;
import com.ogc.standard.enums.EPlayerStatus;
import com.ogc.standard.enums.ERankType;
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

    @Override
    public String orderTicket(String playerCode, Long ticket, String applyUser) {
        Player player = playerBO.getPlayer(playerCode);
        if (!EPlayerStatus.UP.getCode().equals(player.getStatus())) {
            throw new BizException("xn0000", "选手:" + player.getCname() + " 编号:"
                    + player.getMatchPlayCode() + " 不是可加油状态");
        }
        String code = ticketBO.saveTicket(player, ticket, applyUser);
        return code;
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
    public Object toPayTicket(String code, String payType, String tradePwd) {
        Ticket data = ticketBO.getTicket(code);
        if (StringUtils.isNotBlank(tradePwd)) {
            userBO.checkTradePwd(data.getApplyUser(), tradePwd);
        }
        if (ETicketStatus.TO_PAY.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "订单不是待支付状态，不能支付");
        }

        if (EPayType.RMB_YE.getCode().equals(payType)) {
            userBO.checkTradePwd(data.getApplyUser(), tradePwd);
        }

        // 支付订单
        Object result = null;
        if (EPayType.RMB_YE.getCode().equals(payType)) {// 余额支付
            result = toPayTicketYue(data);
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {// 支付宝支付
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "暂不支持支付宝支付");
        } else if (EPayType.WEIXIN_H5.getCode().equals(payType)) {// 微信支付
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "暂不支持微信支付");
        }
        return result;
    }

    // 1、判断余额是否足够，并扣除账户余额
    // 2、更新订单
    // 3、更新选手票数和排名
    @Transactional
    private Object toPayTicketYue(Ticket data) {
        Account userCnyAccount = accountBO.getAccountByUser(
            data.getApplyUser(), ECurrency.CNY.getCode());
        BigDecimal payAmount = data.getAmount();
        if (userCnyAccount.getAmount().compareTo(payAmount) < 0) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(), "人民币账户余额不足");
        }

        // 人民币余额划转
        // Account sysCnyAccount = accountBO
        // .getAccount(ESystemAccount.SYS_ACOUNT_CNY.getCode());
        // accountBO.transAmount(userCnyAccount, sysCnyAccount, payAmount,
        // EJourBizTypeUser.ADOPT.getCode(), EJourBizTypePlat.ADOPT.getCode(),
        // EJourBizTypeUser.ADOPT.getValue(),
        // EJourBizTypePlat.ADOPT.getValue(), data.getCode());

        // 更新业务订单
        ticketBO.payYueSuccess(data);
        // 更新选手票数
        playerBO.refreshPlayerTicketSum(data.getPlayerCode(), data.getTicket());
        // 更新日版榜排名
        Player player = playerBO.getPlayer(data.getPlayerCode());
        String rankDayCode = null;
        Rank rankDay = rankBO.getRankByPlayerCodeAndType(player.getCode(),
            ERankType.DAY.getCode());
        if (null == rankDay) {
            rankDayCode = rankBO.saveRank(player, ERankType.DAY.getCode(),
                data.getTicket());
        } else {
            rankDayCode = rankDay.getCode();
        }
        rankBO.refreshRanking(ERankType.DAY.getCode(), rankDayCode);
        // 更新总版榜排名
        Rank rankTotal = rankBO.getRankByPlayerCodeAndType(player.getCode(),
            ERankType.TOTAL.getCode());
        String rankTotalCode = null;
        if (null == rankTotal) {
            rankTotalCode = rankBO.saveRank(player, ERankType.TOTAL.getCode(),
                data.getTicket());
        } else {
            rankTotalCode = rankTotal.getCode();
        }
        rankBO.refreshRanking(ERankType.TOTAL.getCode(), rankTotalCode);
        return new BooleanRes(true);
    }

    @Override
    public int editTicket(Ticket data) {
        if (!ticketBO.isTicketExist(data.getCode())) {
            throw new BizException("xn0000", "记录编号不存在");
        }
        return ticketBO.refreshTicket(data);
    }

    @Override
    public int dropTicket(String code) {
        if (!ticketBO.isTicketExist(code)) {
            throw new BizException("xn0000", "记录编号不存在");
        }
        return ticketBO.removeTicket(code);
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
            data.setCname(player.getCname());
            data.setEname(player.getEname());
        }
        if (StringUtils.isNotBlank(data.getApplyUser())) {
            // User user = userBO.getUser(data.getApplyUser());

        }
    }

}
