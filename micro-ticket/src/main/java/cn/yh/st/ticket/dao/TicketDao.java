package cn.yh.st.ticket.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.yh.st.ticket.domain.Ticket;

public interface TicketDao extends JpaRepository<Ticket, Long> {

	Ticket findOneByOwner(Long owner);
}
