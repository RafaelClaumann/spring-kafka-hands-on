package br.com.kafka.handson.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.kafka.handson.models.Customer;
import br.com.kafka.handson.models.VipCustomer;

@Component
@KafkaListener(topics = "${app.kafka.topic.created-customer}")
public class CustomerConsumer {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private KafkaTemplate<String, Customer> produceCustomer;

	@Value("${app.kafka.topic.created-customer-retry1}")
	private String createdCustomerRetry;

	public CustomerConsumer(final KafkaTemplate<String, Customer> produceCustomer) {
		this.produceCustomer = produceCustomer;
	}

	@KafkaHandler
	public void consume(@Payload Customer customer, Acknowledgment ack) {
		LOGGER.info("receiving Customer created, name: {}, id: {}", customer.getNome(), customer.getIdentificacao());

		// Simulando uma exceção quando a identificacao do usuario for igual a 5
		if (customer.getIdentificacao().equals("5"))
			produceCustomer.send(createdCustomerRetry, customer).addCallback(success -> {
				LOGGER.info("Sending to retry1 topic = {}", createdCustomerRetry);
				ack.acknowledge();
			}, failure -> {
				LOGGER.info("Sending a nack 5s...");
				ack.nack(5000);
			});
	}

	@KafkaHandler
	public void consume(@Payload VipCustomer vipCustomer, Acknowledgment ack) {
		LOGGER.info("receiving VipCustomer created, name: {}, id: {}, vipId: {}", vipCustomer.getNome(),
				vipCustomer.getIdentificacao(), vipCustomer.getVipId());

		ack.acknowledge();
	}
}
