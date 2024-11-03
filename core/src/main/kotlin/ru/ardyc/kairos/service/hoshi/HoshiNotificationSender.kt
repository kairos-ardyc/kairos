package ru.ardyc.kairos.service.hoshi

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.ardyc.hoshi.avro.NotificationAvro
import java.util.UUID

@Service
class HoshiNotificationSender(
    private val kafkaTemplate: KafkaTemplate<String, NotificationAvro>,
) {
    fun send(userId: UUID, title: String, message: String) {
        kafkaTemplate.send("kai.notification", NotificationAvro(userId.toString(), title, message)).get()
    }
}