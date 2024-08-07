package ru.vsu.strategies.send;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;

import java.util.Collection;

public class TransactionSendStrategy<K, V> implements SendStrategy<K, V> {

    @Override
    public void send(Producer<K, V> kafkaProducer, Collection<ProducerRecord<K, V>> producerRecords, SendStrategyCallback<K, V> callback) {
        try {
            kafkaProducer.beginTransaction();
            for (ProducerRecord<K, V> record : producerRecords) {
                kafkaProducer.send(record, (metadata, exception) -> {
                    callback.onCompletion(record,  metadata, exception);
                });
            }
            kafkaProducer.commitTransaction();
        } catch (KafkaException e) {
            kafkaProducer.abortTransaction();
        }
    }
}
