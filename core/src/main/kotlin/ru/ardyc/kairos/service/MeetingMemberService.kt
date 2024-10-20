package ru.ardyc.kairos.service

import org.springframework.stereotype.Service
import ru.ardyc.kairos.mapper.toResponse
import ru.ardyc.kairos.model.entity.MeetingMember
import ru.ardyc.kairos.repository.MeetingMemberRepository
import java.util.UUID

@Service
class MeetingMemberService(
    private val meetingMemberRepository: MeetingMemberRepository,
    private val tenshiService: TenshiService
) {
    fun isMember(meetingId: UUID, userId: UUID) =
        meetingMemberRepository
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

    fun joinMeeting(userId: UUID, meetingId: UUID) =
        meetingMemberRepository.save(
            meetingId = meetingId,
            userId = userId,
        )


    fun leaveMeeting(userId: UUID, meetingId: UUID) =
        meetingMemberRepository.deleteByMeetingIdAndUserId(meetingId, userId)

    fun removeAllUsers(meetingId: UUID) = meetingMemberRepository.deleteAllByMeetingId(meetingId)
}
