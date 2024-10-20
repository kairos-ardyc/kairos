package ru.ardyc.kairos.service

import org.springframework.stereotype.Service
import ru.ardyc.kairos.model.entity.MeetingMember
import ru.ardyc.kairos.repository.MeetingMemberRepository
import java.util.UUID

@Service
class MeetingMemberService(
    private val meetingMemberRepository: MeetingMemberRepository,
) {
    fun isMember(
        meetingId: UUID,
        userId: UUID,
    ) = meetingMemberRepository
        .getMeetingMembersByUserId(userId)
        .any { meetingUser -> meetingUser.meetingId == meetingId }

    fun getUserMeetings(userId: UUID) =
        meetingMemberRepository
            .getMeetingMembersByUserId(userId)
            .map { meetingUser -> meetingUser.meetingId }

    fun joinMeeting(
        userId: UUID,
        meetingId: UUID,
    ) = meetingMemberRepository.save(
        MeetingMember(
            meetingId = meetingId,
            userId = userId,
        ),
    )


    fun leaveMeeting(
        userId: UUID,
        meetingId: UUID,
    ) =
        meetingMemberRepository
            .deleteByMeetingIdAndUserId(meetingId, userId)
}
