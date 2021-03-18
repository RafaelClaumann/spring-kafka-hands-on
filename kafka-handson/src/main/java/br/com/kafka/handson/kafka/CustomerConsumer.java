package br.com.kafka.handson.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerConsumer {

	//@KafkaListener(topics = "test-topic")
	public void consume(String mensagem) {
		System.out.println(mensagem);
	}
}
