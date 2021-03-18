package br.com.kafka.handson.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	// KafkaTemplate<key, value>
	private final KafkaTemplate<String, String> producerTemplate;

	public CustomerService(final KafkaTemplate<String, String> producerTemplate) {
		this.producerTemplate = producerTemplate;
	}

	public void produce() {
		// send(topic, key, message)
		producerTemplate.send("test-topic", "2", "Mensagem Produzida");
	}

}
