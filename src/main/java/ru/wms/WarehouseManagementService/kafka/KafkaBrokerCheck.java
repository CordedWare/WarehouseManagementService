package ru.wms.WarehouseManagementService.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;

import java.util.Properties;

public class KafkaBrokerCheck {

    // Проверка доступности брокера Кафки
    public static void main(String[] args) {

        Properties configs = new Properties();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        try (AdminClient adminClient = AdminClient.create(configs)) {
            adminClient.describeCluster().nodes().get();

            System.out.println("Брокер доступен.");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
