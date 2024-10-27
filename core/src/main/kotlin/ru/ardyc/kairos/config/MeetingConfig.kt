package ru.ardyc.kairos.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("meeting")
data class MeetingConfig(
    val baseMeetingUrl: String
)