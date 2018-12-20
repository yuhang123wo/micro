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
	private void handleOrderNew(OrderDto dto) {
		System.out.println("handleOrderNew" + dto);
		if (orderDao.findOneByUuid(dto.getUuid()) != null) {
			System.out.println("handleOrderNew is exist" + dto);
		} else {
			Order order = createOrder(dto);
			orderDao.save(order);
		}
		jmsTemplate.convertAndSend("order:pay", dto);
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
