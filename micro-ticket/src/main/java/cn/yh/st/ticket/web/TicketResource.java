package cn.yh.st.ticket.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.yh.dto.OrderDto;
import cn.yh.st.ticket.dao.TicketDao;
import cn.yh.st.ticket.service.TicketService;

@RestController
@RequestMapping("ticket")
public class TicketResource {

	@Autowired
	private TicketDao ticketDao;

	@Autowired
	private TicketService ticketService;

	@RequestMapping("lock")
	public void lock(@RequestBody OrderDto dto) {
		ticketService.ticketLock(dto);
	}
}
