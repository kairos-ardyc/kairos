package ru.ardyc.kairos.model.response

import java.util.UUID

data class MeetingMemberResponse(
    val uuid: UUID,
    val login: String,
    val firstName: String,
    val lastName: String
)