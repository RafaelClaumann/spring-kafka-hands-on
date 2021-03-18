package br.com.kafka.handson.services;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import br.com.kafka.handson.models.Customer;
import br.com.kafka.handson.models.VipCustomer;

@Service
public class CustomerService {

	@Value("${app.kafka.topic.created-customer}")
	private String createdCustomerTopic;

	private Random random = new Random();

	// KafkaTemplate<key, value>
	private final KafkaTemplate<String, Customer> producerCustomerTemplate;
	private final KafkaTemplate<String, VipCustomer> producerVipCustomerTemplate;

	public CustomerService(final KafkaTemplate<String, Customer> producerCustomerTemplate,
			final KafkaTemplate<String, VipCustomer> producerVipCustomerTemplate) {
		this.producerCustomerTemplate = producerCustomerTemplate;
		this.producerVipCustomerTemplate = producerVipCustomerTemplate;
	}

	public ResponseEntity<String> produceBaseCustomer() {
		Customer customer = new Customer("RafaelBaseCustomer", String.valueOf(random.nextInt(10)));

		try {
			// .get() espera pela resposta do broker, similar a um await do javascript.
			SendResult<String, Customer> result = producerCustomerTemplate
					.send(createdCustomerTopic, customer.getIdentificacao(), customer).get();

			return ResponseEntity.accepted().body(result.toString());
		} catch (InterruptedException | ExecutionException e) {
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	public ResponseEntity<String> produceVipCustomer() {
		VipCustomer vipCustomer = new VipCustomer("RafaelVipCustomer", String.valueOf(random.nextInt(10)),
				String.valueOf(random.nextInt(100)));

		try {
			// .get() espera pela resposta do broker, similar a um await do javascript.
			SendResult<String, VipCustomer> result = producerVipCustomerTemplate
					.send(createdCustomerTopic, vipCustomer.getIdentificacao(), vipCustomer).get();
			
			return ResponseEntity.accepted().body(result.toString());
		} catch (InterruptedException | ExecutionException e) {
			return ResponseEntity.unprocessableEntity().build();
		}
	}

}
