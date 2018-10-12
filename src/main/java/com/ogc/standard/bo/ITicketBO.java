package com.ogc.standard.bo;

import java.util.List;

import com.ogc.standard.bo.base.IPaginableBO;
import com.ogc.standard.domain.Player;
import com.ogc.standard.domain.Ticket;

public interface ITicketBO extends IPaginableBO<Ticket> {

    public String saveTicket(Player player, Long ticket, String applyUser);

    public boolean isTicketExist(String code);

    public int removeTicket(String code);

    public int refreshTicket(Ticket data);

    public List<Ticket> queryTicketList(Ticket condition);

    public Ticket getTicket(String code);

    public void refreshCancelTicket(Ticket data, String remark);

    public void payYueSuccess(Ticket data);

}
