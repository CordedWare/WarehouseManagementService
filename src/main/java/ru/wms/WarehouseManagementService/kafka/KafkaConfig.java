package ru.wms.WarehouseManagementService.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic newTopic() {

        return TopicBuilder.name("warehouse_topic")
                .partitions(1)
                .replicas((short) 1)
                .build();
    }
}
