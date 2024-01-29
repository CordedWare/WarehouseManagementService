Процесс установки Kafka на Ubuntu

1)  Cкачать архив https://kafka.apache.org/downloads с нужной версией (качаем именно binary файлы, если не хотим самостоятельно компилировать исходникик)
2)️Разархивировать у себя на компьютере, перейти в директорию с папой bin и выполнить две bash команды, для запуска zookeeper и kafka
    ./zookeeper-server-start.sh ../config/zookeeper.properties
    ./kafka-server-start.sh config/server.properties
3) Проверить тестом KafkaControllerTest
