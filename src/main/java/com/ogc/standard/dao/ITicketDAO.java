package com.ogc.standard.dao;

import com.ogc.standard.dao.base.IBaseDAO;
import com.ogc.standard.domain.Ticket;

//dao层 
public interface ITicketDAO extends IBaseDAO<Ticket> {
    String NAMESPACE = ITicketDAO.class.getName().concat(".");

    int update(Ticket data);

    void updateCancelTicket(Ticket data);
}
