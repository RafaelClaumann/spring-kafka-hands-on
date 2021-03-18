package br.com.kafka.handson.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
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

	public void produceBaseCustomer() {
		Customer customer = new Customer("RafaelBaseCustomer", String.valueOf(random.nextInt(10)));

		// send(topic, key, message)
		producerCustomerTemplate.send(createdCustomerTopic, customer.getIdentificacao(), customer);
	}

	public void produceVipCustomer() {
		VipCustomer vipCustomer = new VipCustomer("RafaelVipCustomer", String.valueOf(random.nextInt(10)),
				String.valueOf(random.nextInt(100)));

		producerVipCustomerTemplate.send(createdCustomerTopic, vipCustomer.getIdentificacao(), vipCustomer);
	}

}
