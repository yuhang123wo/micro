package cn.yh.st.order.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import cn.yh.dto.OrderDto;

@RestController
@RequestMapping("order")
public class OrderResource {

	@Autowired
	private JmsTemplate jmsTemplate;

	private TimeBasedGenerator uuid = Generators.timeBasedGenerator();

	@RequestMapping("create")
	public void create(@RequestBody OrderDto dto) {
		dto.setUuid(uuid.generate().toString());
		jmsTemplate.convertAndSend("order:new", dto);
	}
}
