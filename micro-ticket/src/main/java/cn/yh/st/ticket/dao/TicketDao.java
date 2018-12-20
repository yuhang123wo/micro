package cn.yh.st.ticket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.yh.st.ticket.domain.Ticket;

public interface TicketDao extends JpaRepository<Ticket, Long> {

	Ticket findOneByOwner(Long owner);

	@Modifying
	@Query(value = "update t_ticket set lockUser=?1 where ticketNum=?2 and lockUser is null")
	int updateOwner(long lockUser, long ticketNum);
}
