package cn.yh.st.ticket.web;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.yh.st.ticket.dao.TicketDao;
import cn.yh.st.ticket.domain.Ticket;

@RestController
@RequestMapping("ticket")
public class TicketResource {

	@Autowired
	private TicketDao ticketDao;

	@PostConstruct
	public void init() {
		Ticket t = new Ticket();
		t.setName("Num.01");
		t.setTicketNum(100L);
		ticketDao.save(t);

	}
}
