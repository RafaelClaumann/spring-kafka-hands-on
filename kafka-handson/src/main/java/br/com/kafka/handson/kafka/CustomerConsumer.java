package br.com.kafka.handson.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.kafka.handson.models.Customer;
import br.com.kafka.handson.models.VipCustomer;

@Component
@KafkaListener(topics = "${app.kafka.topic.created-customer}")
public class CustomerConsumer {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	// Acknowledgment é injetado pelo spring devido a propriedade: spring.kafka.consumer.enable-auto-commit=false
	@KafkaHandler
	public void consume(@Payload Customer customer, Acknowledgment ack) {
		LOGGER.info("receiving Customer created, name: {}, id: {}", customer.getNome(), customer.getIdentificacao());
		ack.acknowledge();  // faz o commit
	}

	@KafkaHandler
	public void consume(@Payload VipCustomer vipCustomer,Acknowledgment ack) {
		LOGGER.info("receiving VipCustomer created, name: {}, id: {}, vipId: {}", vipCustomer.getNome(),
				vipCustomer.getIdentificacao(), vipCustomer.getVipId());
		
		// faz um RETRY a cada 5 segundos até conseguir commitar a mensagem, nesse caso vai ficar em loop eterno.
		ack.nack(5000);
	}
}
