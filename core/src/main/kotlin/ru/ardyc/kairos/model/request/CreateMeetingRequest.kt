package ru.ardyc.kairos.model.request

import java.time.Instant

data class CreateMeetingRequest(
    val title: String,
    val description: String = "",
    val start: Instant = Instant.now(),
    val end: Instant = start.plusSeconds(600),
)
