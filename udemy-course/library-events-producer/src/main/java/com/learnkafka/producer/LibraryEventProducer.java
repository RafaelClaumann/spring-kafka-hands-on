package com.learnkafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.domain.LibraryEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class LibraryEventProducer {

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {
        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);

        ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.sendDefault(key, value);
        listenableFuture.addCallback(success -> {
            handleSuccess(key, value, success);
        }, failure -> {
            handleFailure(key, value, failure);
        });
    }

    public void sendLibraryEventWithTopicName(LibraryEvent libraryEvent) throws JsonProcessingException {
        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);
        String topic = "library-events";

        ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, topic);

        // ListenableFuture<SendResult<K, V>> send(String topic, K key, @Nullable V data)
        ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord);
        listenableFuture.addCallback(success -> {
            handleSuccess(key, value, success);
        }, failure -> {
            handleFailure(key, value, failure);
        });
    }

    public void sendLibraryEventSynchronous(LibraryEvent libraryEvent) throws JsonProcessingException {
        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);

        try {
            // will wait until the ListenableFuture return Successfully or Failure
            kafkaTemplate.sendDefault(key, value).get();
        } catch (InterruptedException | ExecutionException ex) {
            log.error("InterruptedException/ExecutionException sending the message and the exception is {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("Exception sending the message and the exception is {}", ex.getMessage());
        }
    }

    private ProducerRecord<Integer, String> buildProducerRecord(final Integer key, final String value, final String topic) {
        List<Header> headers = List.of(new RecordHeader("event-source", ("scanner").getBytes(StandardCharsets.UTF_8)));

        //  ProducerRecord(String topic, Integer partition, K key, V value, Iterable<Header> headers)
        return new ProducerRecord<>(topic, null, key, value, headers);
    }

    private void handleSuccess(final Integer key, final String value, final SendResult<Integer, String> result) {
        log.info("Message sent successfully for the key: {} and the value is {}, partition is {}", key, value, result.getRecordMetadata().partition());
    }

    private void handleFailure(final Integer key, final String value, final Throwable ex) {
        log.error("Error sending the message and the exception is {}", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }
}
