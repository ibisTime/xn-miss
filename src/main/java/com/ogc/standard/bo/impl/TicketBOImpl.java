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
import com.ogc.standard.enums.ETicketStatus;
import com.ogc.standard.exception.BizException;

@Component
public class TicketBOImpl extends PaginableBOImpl<Ticket> implements ITicketBO {

    @Autowired
    private ITicketDAO ticketDAO;

    @Override
    public String saveTicket(Player player, Long ticket, String applyUser) {
        String code = null;
        if (null != player) {
            code = OrderNoGenerater.generate(EGeneratePrefix.TICKET.getCode());
            Ticket data = new Ticket();
            data.setPlayerCode(player.getCode());
            data.setTicket(ticket);
            data.setAmount(BigDecimal.valueOf(ticket * 1));// TODO
            data.setApplyUser(applyUser);
            data.setStatus(ETicketStatus.TO_PAY.getCode());
            data.setCreateDatetime(new Date());
            Date invalidDatetime = addDate(data.getCreateDatetime(), 30);// TODO
            data.setInvalidDatetime(invalidDatetime);
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
    public boolean isTicketExist(String code) {
        Ticket condition = new Ticket();
        condition.setCode(code);
        if (ticketDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int removeTicket(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Ticket data = new Ticket();
            data.setCode(code);
            count = ticketDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshTicket(Ticket data) {
        int count = 0;
        if (StringUtils.isNotBlank(data.getCode())) {
            count = ticketDAO.update(data);
        }
        return count;
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

    public static Date addDate(Date date, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        date = cal.getTime();
        cal = null;
        return date;
    }

}
