package br.com.kafka.handson.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.kafka.handson.models.Customer;
import br.com.kafka.handson.models.VipCustomer;

@Component
@KafkaListener(topics = "${app.kafka.topic.created-customer}")
public class CustomerConsumer {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@KafkaHandler
	public void consume(@Payload Customer customer) {
		LOGGER.info("receiving Customer created, name: {}, id: {}", customer.getNome(), customer.getIdentificacao());
	}

	@KafkaHandler
	public void consume(@Payload VipCustomer vipCustomer) {
		LOGGER.info("receiving VipCustomer created, name: {}, id: {}, vipId: {}", vipCustomer.getNome(),
				vipCustomer.getIdentificacao(), vipCustomer.getVipId());
	}
}
