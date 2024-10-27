package ru.ardyc.kairos.mapper

import ru.ardyc.kairos.model.entity.Meeting
import ru.ardyc.kairos.model.request.CreateMeetingRequest
import java.time.Instant
import java.util.UUID

fun CreateMeetingRequest.toMeeting(ownerId: UUID) = Meeting(
    uuid = UUID.randomUUID(),
    owner = ownerId,
    title = title ?: "",
    room = room ?: "",
    description = description ?: "",
    start = start ?: Instant.now(),
    end = end ?: Instant.now(),
)