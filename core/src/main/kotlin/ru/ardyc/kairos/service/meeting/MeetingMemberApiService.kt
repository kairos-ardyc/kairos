package ru.ardyc.kairos.service.meeting

import org.springframework.stereotype.Service
import ru.ardyc.kairos.mapper.toResponse
import ru.ardyc.kairos.repository.MeetingMemberRepository
import ru.ardyc.kairos.service.tenshi.TenshiService
import java.util.UUID

@Service
class MeetingMemberApiService(
    private val meetingMemberRepository: MeetingMemberRepository,
    private val tenshiService: TenshiService
) {
    fun isMember(userId: UUID, meetingId: UUID) = meetingMemberRepository
        .getMeetingMembersByUserId(userId)
        .any { meetingUser -> meetingUser.meetingId == meetingId }

    fun getUserMeetings(userId: UUID) =
        meetingMemberRepository
            .getMeetingMembersByUserId(userId)
            .map { meetingUser -> meetingUser.meetingId }

    fun getUsersByMeeting(meetingId: UUID) =
        meetingMemberRepository
            .getMeetingMembersByMeetingId(meetingId)
            .map { it.toResponse(tenshiService.getUserById(it.userId)) }

    fun addUserToMeeting(userId: UUID, meetingId: UUID) =
        meetingMemberRepository.save(
            meetingId = meetingId,
            userId = userId,
        )

    fun removeUserFromMeeting(userId: UUID, meetingId: UUID) =
        meetingMemberRepository.deleteByMeetingIdAndUserId(meetingId, userId)

    fun removeAllUsers(meetingId: UUID) = meetingMemberRepository.deleteAllByMeetingId(meetingId)
}
