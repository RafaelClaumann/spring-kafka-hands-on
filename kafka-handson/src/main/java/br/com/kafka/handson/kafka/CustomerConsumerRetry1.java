package br.com.kafka.handson.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.kafka.handson.models.Customer;

@Component
@KafkaListener(topics = "${app.kafka.topic.created-customer-retry1}")
public class CustomerConsumerRetry1 {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@KafkaHandler
	public void consume(@Payload Customer customer, Acknowledgment ack) {
		try {
			LOGGER.info("####### BEGIN RETRY 1 - {} ##############", customer.getIdentificacao());
			LOGGER.info("[RETRY 1] Receiving Customer Created {} - {}", customer.getNome(), customer.getIdentificacao());
			ack.acknowledge();
		} finally {
			LOGGER.info("####### END RETRY 1 - {} ##############", customer.getIdentificacao());
		}
	}
}
