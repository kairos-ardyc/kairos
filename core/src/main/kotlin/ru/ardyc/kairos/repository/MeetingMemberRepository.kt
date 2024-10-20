package ru.ardyc.kairos.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.ardyc.kairos.model.entity.MeetingMember
import java.util.UUID

@Repository
interface MeetingMemberRepository : CrudRepository<MeetingMember, UUID> {
    fun getMeetingMembersByUserId(userId: UUID): List<MeetingMember>

    @Modifying
    fun deleteByMeetingIdAndUserId(
        meetingId: UUID,
        userId: UUID,
    )
}
