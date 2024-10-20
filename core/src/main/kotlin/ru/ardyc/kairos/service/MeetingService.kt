package ru.ardyc.kairos.service

import org.springframework.stereotype.Service
import ru.ardyc.kairos.mapper.toResponse
import ru.ardyc.kairos.model.entity.Meeting
import ru.ardyc.kairos.model.request.CreateMeetingRequest
import ru.ardyc.kairos.model.response.MeetingResponse
import ru.ardyc.kairos.repository.MeetingRepository
import java.util.UUID

@Service
class MeetingService(
    private val meetingRepository: MeetingRepository,
    private val meetingMemberService: MeetingMemberService
) {
    fun createMeeting(
        userId: UUID,
        request: CreateMeetingRequest,
    ) = meetingRepository.save(
        Meeting(
            uuid = UUID.randomUUID(),
            owner = userId,
            title = request.title,
            room = request.room,
            description = request.description,
            start = request.start,
            end = request.end,
        ),
    )
        .also { meetingMemberService.joinMeeting(userId, it.uuid) }
        .run { toResponse(meetingMemberService.getUsersByMeeting(uuid)) }

    fun getMeeting(meetingId: UUID): MeetingResponse =
        meetingRepository
            .findById(meetingId)
            .orElseThrow { IllegalArgumentException("Meeting with id $meetingId not found") }
            .toResponse(
                meetingMemberService.getUsersByMeeting(meetingId)
            )

    fun leaveMeeting(userId: UUID, meetingId: UUID) =
        with(getMeeting(meetingId)) {
            if (owner == userId) {
                meetingMemberService.removeAllUsers(meetingId)
                meetingRepository.deleteById(meetingId)
            }
            meetingMemberService.leaveMeeting(userId, meetingId)
        }
}
