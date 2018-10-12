package com.ogc.standard.ao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ogc.standard.bo.base.Paginable;
import com.ogc.standard.domain.Ticket;

@Component
public interface ITicketAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String orderTicket(String playerCode, Long ticket, String applyUser);

    public void cancelTicket(String code, String userId, String remark);

    public Object toPayTicket(String code, String payType, String tradePwd);

    public int dropTicket(String code);

    public int editTicket(Ticket data);

    public Paginable<Ticket> queryTicketPage(int start, int limit,
            Ticket condition);

    public List<Ticket> queryTicketList(Ticket condition);

    public Ticket getTicket(String code);

}
