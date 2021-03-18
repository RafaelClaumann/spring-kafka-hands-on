package br.com.kafka.handson.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.kafka.handson.models.Customer;

@Service
public class CustomerService {

	// KafkaTemplate<key, value>
	private final KafkaTemplate<String, Customer> producerTemplate;

	public CustomerService(final KafkaTemplate<String, Customer> producerTemplate) {
		this.producerTemplate = producerTemplate;
	}

	public void produce() {
		// send(topic, key, message)
		producerTemplate.send("test-topic-json", "2", new Customer("Rafael", "000.000.000-00"));
	}

}
