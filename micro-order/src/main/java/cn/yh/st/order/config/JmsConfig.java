package cn.yh.st.order.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JmsConfig {
	@Bean
	public ConnectionFactory connectionFactory() {
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
		TransactionAwareConnectionFactoryProxy proxy = new TransactionAwareConnectionFactoryProxy();
		proxy.setTargetConnectionFactory(cf);
		proxy.setSynchedLocalTransactionAllowed(true);
		return proxy;
	}

	@Bean
	public JmsTemplate initJmsTemplate(ConnectionFactory connectionFactory,
			MessageConverter jacksonJmsMessageConverter) {
		JmsTemplate template = new JmsTemplate(connectionFactory);
		template.setMessageConverter(jacksonJmsMessageConverter);
		template.setSessionTransacted(true);
		return template;
	}

	// 这个用于设置 @JmsListener使用的containerFactory
	@Bean
	public JmsListenerContainerFactory<?> msgFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer,
			PlatformTransactionManager transactionManager) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setTransactionManager(transactionManager);
		factory.setReceiveTimeout(10000L);
		factory.setConcurrency("10");
		configurer.configure(factory, connectionFactory);
		return factory;
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {

		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
}