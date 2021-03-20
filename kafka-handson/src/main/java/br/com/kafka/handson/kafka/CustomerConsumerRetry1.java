package br.com.kafka.handson.kafka;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.kafka.handson.models.Customer;

@Component
@KafkaListener(topics = "${app.kafka.topic.created-customer-retry1}")
public class CustomerConsumerRetry1 {

	private static final int WAIT_TIME_SECONDS = 10;
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Value("${app.kafka.topic.created-customer-dlq}")
	private String createdCustomerDlq;

	private final KafkaTemplate<String, Customer> producerCustomer;

	public CustomerConsumerRetry1(KafkaTemplate<String, Customer> producerCustomer) {
		this.producerCustomer = producerCustomer;
	}

	@KafkaHandler
	public void consume(@Payload Customer customer, Acknowledgment ack,
			@Header(name = KafkaHeaders.RECEIVED_TIMESTAMP) String timestamp) {

		try {
			LOGGER.info("####### BEGIN RETRY 1 - {} ##############", customer.getIdentificacao());

			LocalDateTime dateTime = Instant.ofEpochMilli(Long.valueOf(timestamp)).atZone(ZoneId.systemDefault()).toLocalDateTime();
			LOGGER.info("message received at {}", dateTime);

			LocalDateTime desiredDateTime = dateTime.plusSeconds(WAIT_TIME_SECONDS);
			LOGGER.info("message should be processed at {}", desiredDateTime);

			// customer com identificacao igual a 5 é enviado para o retry
			// além disso, este cliente também é enviado pada o DLQ (Dead Letter Queue)
			// Isso é simução de uma exceção
			long diffSeconds = ChronoUnit.SECONDS.between(dateTime, LocalDateTime.now());
			if (WAIT_TIME_SECONDS <= diffSeconds) {
			
				if (customer.getIdentificacao().equals("5")) {
					producerCustomer.send(createdCustomerDlq, customer).addCallback(success -> {
						LOGGER.info("Sending to DLQ topic = {}", createdCustomerDlq);
						ack.acknowledge();
					}, failure -> {
						LOGGER.info("Sending a nack 5s...");
						ack.nack(50000);
					});
				} else {
					ack.acknowledge();
				}

			} else {
				LOGGER.info("waiting more {} seconds", (WAIT_TIME_SECONDS - diffSeconds));
				ack.nack((WAIT_TIME_SECONDS - diffSeconds) * 1000);
			}

			LOGGER.info("[RETRY 1] Receiving Customer Created {} - {}", customer.getNome(), customer.getIdentificacao());
			ack.acknowledge();
		} finally {
			LOGGER.info("####### END RETRY 1 - {} ##############", customer.getIdentificacao());
		}
	}
}
