package cn.yh.st.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yh.dto.OrderDto;
import cn.yh.st.order.dao.OrderDao;
import cn.yh.st.order.domain.Order;

@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private JmsTemplate jmsTemplate;

	@Transactional
	@JmsListener(destination = "order:locked", containerFactory = "msgFactory")
	public void handleOrderNew(OrderDto dto) {
		System.out.println("handleOrderNew" + dto);
		if (orderDao.findOneByUuid(dto.getUuid()) != null) {
			System.out.println("handleOrderNew is exist" + dto);
		} else {
			Order order = createOrder(dto);
			orderDao.save(order);
			dto.setId(order.getId());
		}
		jmsTemplate.convertAndSend("order:pay", dto);
	}

	@Transactional
	@JmsListener(destination = "order:done", containerFactory = "msgFactory")
	public void handleOrderDone(OrderDto dto) {
		Order order = orderDao.findOneById(dto.getId());
		order.setStatus("done");
		orderDao.save(order);
	}

	@JmsListener(destination = "order:fail", containerFactory = "msgFactory")
	@Transactional
	public void handleTicketLock(OrderDto orderDto) {
		if (orderDto.getId() == null) {
			Order order = createOrder(orderDto);
			order.setStatus("FAIL");
			order.setReason("LOCKED_FAIL");
			orderDao.save(order);
		} else {
			Order order = orderDao.findOneById(orderDto.getId());
			orderDto.setStatus("TICKET_LOCKED_FAIL");
			jmsTemplate.convertAndSend("order:fail", orderDto);
		}
	}

	private Order createOrder(OrderDto dto) {
		Order o = new Order();
		o.setAmount(dto.getAmount());
		o.setCusomerId(dto.getCustomerId());
		o.setTicketNum(dto.getTicketNum());
		o.setUuid(dto.getUuid());
		o.setStatus("new");
		o.setTitle(dto.getTitle());
		return o;
	}
}
