package ru.vsu.strategies;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

public class SimpleSendStrategy<K, V> implements SendStrategy<K, V> {

    @Override
    public void send(KafkaProducer<K, V> kafkaProducer, ProducerRecord<K, V> record) throws ExecutionException, InterruptedException {
        kafkaProducer.send(record).get();
    }

    @Override
    public void send(KafkaProducer<K, V> kafkaProducer, Collection<ProducerRecord<K, V>> producerRecords) throws ExecutionException, InterruptedException {
        for (ProducerRecord<K, V> record : producerRecords) {
            kafkaProducer.send(record).get();
        }
    }
}
