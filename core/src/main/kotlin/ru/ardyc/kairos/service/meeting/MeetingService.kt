package ru.ardyc.kairos.service.meeting

import org.springframework.stereotype.Service
import ru.ardyc.kairos.entrypoint.security.userContext
import ru.ardyc.kairos.entrypoint.security.userId
import ru.ardyc.kairos.mapper.toMeeting
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
        request: CreateMeetingRequest,
    ) = userContext { user ->
        meetingRepository.save(request.toMeeting(user.userId))
            .also { meetingMemberService.addUserToMeeting(user.userId, it.uuid) }
            .run { toResponse(meetingMemberService.getUsersByMeeting(uuid)) }
    }

    fun getMeeting(meetingId: UUID): MeetingResponse =
        findMeetingById(meetingId)
            .toResponse(meetingMemberService.getUsersByMeeting(meetingId))

    fun deleteById(meetingId: UUID) = userContext { user ->
        findMeetingById(meetingId)
            .checkOwner(user.userId)
            .also {
                meetingMemberService.removeAllUsers(meetingId)
                meetingRepository.deleteById(meetingId)
            }
    }

    fun editMeeting(meetingId: UUID, request: CreateMeetingRequest) = userContext { user ->
        findMeetingById(meetingId)
            .checkOwner(user.userId)
            .applyModifications(request)
            .toResponse(meetingMemberService.getUsersByMeeting(meetingId))
    }

    fun getMeetings() = meetingRepository.findAll()
        .map { it.toResponse(meetingMemberService.getUsersByMeeting(it.id)) }


    private fun Meeting.applyModifications(request: CreateMeetingRequest) = apply {
        request.title?.let { title = it }
        request.room?.let { room = it }
        request.description?.let { description = it }
        request.start?.let { start = it }
        request.end?.let { end = it }
    }

    private fun Meeting.checkOwner(userId: UUID) = if (owner != userId) {
        throw IllegalArgumentException("User with id $userId is not owner of meeting with id $uuid")
    } else this

    private fun findMeetingById(meetingId: UUID) = meetingRepository
        .findById(meetingId)
        .orElseThrow { IllegalArgumentException("Meeting with id $meetingId not found") }
}
