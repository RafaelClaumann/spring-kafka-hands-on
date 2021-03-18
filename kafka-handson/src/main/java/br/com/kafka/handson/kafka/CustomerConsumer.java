package br.com.kafka.handson.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import br.com.kafka.handson.models.BaseCustomer;

@Component
public class CustomerConsumer {

	@KafkaListener(topics = "test-topic-json")
	public void consume(BaseCustomer customer) {
		System.out.println(customer.toString());
	}
}
