# **Название приложения**

**Описание:**

Это веб-приложение, которое использует Kafka Java для обработки и передачи данных.

**Основные функции:**
* Чтение данных из Kafka;
* Обработка данных с использованием Java;
* Запись обработанных данных обратно в Kafka.

**Установка:**
1. Запустить order_service\docker\docker-start.cmd
2. Загрузите приложение order_service, а затем order_status_service

**Использование:**
Приложение order_service позволяет отправлять сообщения в первый топик kafkaOrderTopic, далее приложение "слушает" топик kafkaOrderTopic, 
если приходит сообщение приложение его обрабатывает и отправляет новое сообщение в топик kafkaEventTopic.

**Эндпоинт:**
POST: /api/v1/kafka
приложение принимает JSON объект Order

