package ru.ardyc.kairos.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.ardyc.kairos.model.entity.MeetingMember
import java.util.UUID

@Repository
interface MeetingMemberRepository : CrudRepository<MeetingMember, UUID> {

    fun getMeetingMembersByUserId(userId: UUID): List<MeetingMember>
    fun getMeetingMembersByMeetingId(meetingId: UUID): List<MeetingMember>

    fun deleteByMeetingIdAndUserId(
        meetingId: UUID,
        userId: UUID,
    )

    @Modifying
    @Query("DELETE FROM meeting_member WHERE meeting_id = :meetingId")
    fun deleteAllByMeetingId(meetingId: UUID)

    @Modifying
    @Query("INSERT INTO meeting_member (meeting_id, user_id) VALUES (:meetingId, :userId)")
    fun save(meetingId: UUID, userId: UUID)
}
