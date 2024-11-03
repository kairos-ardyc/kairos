package ru.ardyc.kairos.config.kafka

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kafka")
data class KafkaProperties(
    val bootstrapServers: String,
    val schemaRegistryUrl: String
)