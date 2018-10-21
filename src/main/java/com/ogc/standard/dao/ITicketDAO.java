package com.ogc.standard.dao;

import com.ogc.standard.dao.base.IBaseDAO;
import com.ogc.standard.domain.Ticket;

public interface ITicketDAO extends IBaseDAO<Ticket> {
    String NAMESPACE = ITicketDAO.class.getName().concat(".");

    public Ticket selectForUpdate(Ticket condition);

    void updateCancelTicket(Ticket data);

    void updatePayYueSuccess(Ticket data);

    void updatePayGroup(Ticket data);

    void updatePaySuccess(Ticket data);
}
