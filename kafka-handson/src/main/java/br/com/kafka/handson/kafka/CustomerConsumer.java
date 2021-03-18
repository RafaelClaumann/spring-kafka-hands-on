package br.com.kafka.handson.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import br.com.kafka.handson.models.Customer;

@Component
public class CustomerConsumer {

	@KafkaListener(topics = "test-topic-json")
	public void consume(Customer customer) {
		System.out.println(customer.toString());
	}
}
