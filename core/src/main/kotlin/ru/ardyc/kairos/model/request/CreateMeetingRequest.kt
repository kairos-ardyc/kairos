package ru.ardyc.kairos.model.request

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant

data class CreateMeetingRequest(
    val title: String? = null,
    val room: String? = null,
    val description: String? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    val start: Instant? = Instant.now(),
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    val end: Instant? = start?.plusSeconds(600),
)
