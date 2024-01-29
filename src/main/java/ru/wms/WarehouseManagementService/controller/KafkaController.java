package ru.wms.WarehouseManagementService.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.wms.WarehouseManagementService.kafka.KafkaProducer;

@RestController
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    public KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/kafka-warehouse/send-message")
    public String send(@RequestBody String message) {

        kafkaProducer.sendMessage(message);

        return "Успешно";
    }
}
