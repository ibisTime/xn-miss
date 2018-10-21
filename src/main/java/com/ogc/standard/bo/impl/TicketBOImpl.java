package com.ogc.standard.bo.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ogc.standard.bo.ITicketBO;
import com.ogc.standard.bo.base.PaginableBOImpl;
import com.ogc.standard.core.OrderNoGenerater;
import com.ogc.standard.dao.ITicketDAO;
import com.ogc.standard.domain.Player;
import com.ogc.standard.domain.Ticket;
import com.ogc.standard.enums.EGeneratePrefix;
import com.ogc.standard.enums.EPayType;
import com.ogc.standard.enums.ETicketStatus;
import com.ogc.standard.exception.BizException;

@Component
public class TicketBOImpl extends PaginableBOImpl<Ticket> implements ITicketBO {

    @Autowired
    private ITicketDAO ticketDAO;

    @Override
    public String saveTicket(Player player, Long ticket, String applyUser,
            BigDecimal price, int invalidTime) {
        String code = null;
        if (null != player) {
            Ticket data = new Ticket();
            code = OrderNoGenerater.generate(EGeneratePrefix.TICKET.getCode());
            data.setCode(code);
            data.setPlayerCode(player.getCode());
            data.setTicket(ticket);
            data.setAmount(price.multiply(new BigDecimal(ticket)));
            data.setApplyUser(applyUser);
            data.setStatus(ETicketStatus.TO_PAY.getCode());
            data.setCreateDatetime(new Date());
            Date invalidDatetime = addDate(data.getCreateDatetime(),
                invalidTime);
            data.setInvalidDatetime(invalidDatetime);
            data.setRemark("待支付订单");
            ticketDAO.insert(data);
        }
        return code;
    }

    @Override
    public void refreshCancelTicket(Ticket data, String remark) {
        if (null != data) {
            data.setStatus(ETicketStatus.CANCEL.getCode());
            data.setCanceler(data.getApplyUser());
            data.setCancelDatetime(new Date());
            data.setRemark(remark);
            ticketDAO.updateCancelTicket(data);
        }
    }

    @Override
    public List<Ticket> queryTicketList(Ticket condition) {
        return ticketDAO.selectList(condition);
    }

    @Override
    public Ticket getTicket(String code) {
        Ticket data = null;
        if (StringUtils.isNotBlank(code)) {
            Ticket condition = new Ticket();
            condition.setCode(code);
            data = ticketDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "订单不存在");
            }
        }
        return data;
    }

    @Override
    public Ticket getTicketForUpdate(String code) {
        Ticket data = null;
        if (StringUtils.isNotBlank(code)) {
            Ticket condition = new Ticket();
            condition.setCode(code);
            data = ticketDAO.selectForUpdate(condition);
            if (data == null) {
                throw new BizException("xn0000", "订单不存在");
            }
        }
        return data;
    }

    public static Date addDate(Date date, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        date = cal.getTime();
        cal = null;
        return date;
    }

    @Override
    public void payYueSuccess(Ticket data) {
        if (null != data) {
            data.setStatus(ETicketStatus.PAYED.getCode());
            data.setPayType(EPayType.RMB_YE.getCode());
            data.setPayAmount(data.getAmount());
            data.setPayDatetime(new Date());
            data.setRemark("余额支付成功");
            ticketDAO.updatePayYueSuccess(data);
        }
    }

    @Override
    public String addPayGroup(Ticket data, String payType) {
        String payGroup = null;
        if (null != data) {
            data.setPayType(payType);
            data.setPayGroup(data.getCode());
            data.setRemark("发起支付请求");
            ticketDAO.updatePayGroup(data);
        }
        return payGroup;
    }

}
