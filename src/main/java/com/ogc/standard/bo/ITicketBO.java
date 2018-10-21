package com.ogc.standard.bo;

import java.math.BigDecimal;
import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Player;
import com.ogc.standard.domain.Ticket;

public interface ITicketBO extends IPaginableBO<Ticket> {

    public String saveTicket(Player player, Long ticket, String applyUser,
            BigDecimal price, int invalidTime);

    public List<Ticket> queryTicketList(Ticket condition);

    public Ticket getTicket(String code);

    // public Ticket getTicketForUpdate(String code);

    public void refreshCancelTicket(Ticket data, String remark);

    public void payYueSuccess(Ticket data);

    public String addPayGroup(Ticket data, String payType);

    public void paySuccess(Ticket data, String payCode);

}
