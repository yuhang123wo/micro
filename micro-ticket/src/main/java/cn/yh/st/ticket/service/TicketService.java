package cn.yh.st.ticket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import cn.yh.dto.OrderDto;

@Service
public class TicketService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@JmsListener(destination = "order:new", containerFactory = "msgFactory")
	public void handleTicketLock(OrderDto orderDto) {
		log.info("handleTicketLock" + orderDto);

	}

	public void ticketLock(OrderDto dto) {

	}
}
