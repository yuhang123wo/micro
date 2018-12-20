package cn.yh.st.ticket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yh.dto.OrderDto;
import cn.yh.st.ticket.dao.TicketDao;

@Service
public class TicketService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JmsTemplate jmsTemplate;

	@JmsListener(destination = "order:new", containerFactory = "msgFactory")
	public void handleTicketLock(OrderDto orderDto) {
		log.info("handleTicketLock" + orderDto);
		int count = this.ticketLock(orderDto);
		if (count == 1) {
			orderDto.setStatus("TICKET_LOCKED");
			jmsTemplate.convertAndSend("order:locked", orderDto);
		} else {

		}
	}

	@Transactional
	public int ticketLock(OrderDto dto) {
		int n = ticketDao.updateOwner(dto.getCustomerId(), dto.getTicketNum());
		System.out.println("n=" + n);
		return n;
	}

	@Autowired
	private TicketDao ticketDao;

	@Transactional
	@JmsListener(destination = "order:ticket_move", containerFactory = "msgFactory")
	public void handleTicketMove(OrderDto orderDto) {
		int count = ticketDao.moveTicket(orderDto.getCustomerId(), orderDto.getTicketNum());
		if (count == 0) {
			System.out.println("alreay move");

		}
		orderDto.setStatus("moved");
		jmsTemplate.convertAndSend("order:ticket_done", orderDto);
	}
}
