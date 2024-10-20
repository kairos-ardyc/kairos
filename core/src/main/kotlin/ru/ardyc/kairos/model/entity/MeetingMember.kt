package ru.ardyc.kairos.model.entity

import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("meeting_member")
data class MeetingMember(
    val meetingId: UUID,
    val userId: UUID,
)
