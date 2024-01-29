package ru.kafkaproject.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

// Будет читать компонент из Кафки
@Service
public class KafkaConsumer {

    @KafkaListener(topics = "warehouse_topic", groupId = "warehouse_consumer")
    public void listen(String message) {
        System.out.println("Получено сообщение = " + message);
    }


}
