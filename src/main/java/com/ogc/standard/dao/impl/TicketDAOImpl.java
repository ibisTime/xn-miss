package com.ogc.standard.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ogc.standard.dao.ITicketDAO;
import com.ogc.standard.dao.base.support.AMybatisTemplate;
import com.ogc.standard.domain.Ticket;

@Repository("ticketDAOImpl")
public class TicketDAOImpl extends AMybatisTemplate implements ITicketDAO {

    @Override
    public int insert(Ticket data) {
        return super.insert(NAMESPACE.concat("insert_ticket"), data);
    }

    @Override
    public int delete(Ticket data) {
        return super.delete(NAMESPACE.concat("delete_ticket"), data);
    }

    @Override
    public Ticket select(Ticket condition) {
        return super.select(NAMESPACE.concat("select_ticket"), condition,
            Ticket.class);
    }

    @Override
    public long selectTotalCount(Ticket condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_ticket_count"),
            condition);
    }

    @Override
    public List<Ticket> selectList(Ticket condition) {
        return super.selectList(NAMESPACE.concat("select_ticket"), condition,
            Ticket.class);
    }

    @Override
    public List<Ticket> selectList(Ticket condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_ticket"), start,
            count, condition, Ticket.class);
    }

    @Override
    public int update(Ticket data) {
        return 0;
    }

    @Override
    public void updateCancelTicket(Ticket data) {
        super.update(NAMESPACE.concat("update_cancel_ticket"), data);
    }

    @Override
    public void updatePayYueSuccess(Ticket data) {
        super.update(NAMESPACE.concat("update_pay_yue_success"), data);
    }

}
