package ru.ardyc.kairos.model.response

import java.util.UUID

data class MeetingMemberResponse(
    val id: UUID,
    val firstName: String,
    val lastName: String
)