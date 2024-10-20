package ru.ardyc.kairos.model.response

import java.time.Instant
import java.util.UUID

data class MeetingResponse(
    val uuid: UUID,
    val owner: UUID,
    val title: String,
    val room: String,
    val description: String,
    val start: Instant,
    val end: Instant,
    val members: List<MeetingMemberResponse>,
)