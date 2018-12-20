package cn.yh.st.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yh.dto.OrderDto;
import cn.yh.st.user.dao.PayDao;
import cn.yh.st.user.dao.UserDao;
import cn.yh.st.user.domain.PayInfo;
import cn.yh.st.user.domain.User;

@Service
public class UserService {
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PayDao payDao;

	@JmsListener(destination = "order:pay", containerFactory = "msgFactory")
	@Transactional
	public void handleOrderPay(OrderDto orderDto) {
		PayInfo pay = payDao.findByOrderId(orderDto.getId());
		if (pay != null) {
			System.out.println("pay!=null");
			return;
		}
		User user = userDao.findOneById(orderDto.getCustomerId());
		if (user.getDeposit() < orderDto.getAmount()) {
			return;
		}
		pay = new PayInfo();
		pay.setOrderId(orderDto.getId());
		pay.setAmount(orderDto.getAmount());
		pay.setStatus("PAID");
		payDao.save(pay);
		user.setDeposit(user.getDeposit() - orderDto.getAmount());
		userDao.charge(orderDto.getCustomerId(), orderDto.getAmount());

		orderDto.setStatus("PAID");
		jmsTemplate.convertAndSend("order:ticket_move", orderDto);
	}
}
