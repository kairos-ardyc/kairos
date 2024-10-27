package ru.ardyc.kairos.service.meeting

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import ru.ardyc.kairos.config.MeetingConfig
import ru.ardyc.kairos.mapper.toMeeting
import ru.ardyc.kairos.mapper.toResponse
import ru.ardyc.kairos.model.entity.Meeting
import ru.ardyc.kairos.model.request.CreateMeetingRequest
import ru.ardyc.kairos.model.response.UrlResponse
import ru.ardyc.kairos.repository.MeetingRepository
import java.util.UUID

@Service
@EnableConfigurationProperties(MeetingConfig::class)
class MeetingApiService(
    private val meetingRepository: MeetingRepository,
    private val meetingMemberApiService: MeetingMemberApiService,
    private val meetingConfig: MeetingConfig
) {

    fun createMeeting(
        requester: UUID,
        request: CreateMeetingRequest,
    ) = meetingRepository.save(request.toMeeting(requester))
        .also { meetingMemberApiService.addUserToMeeting(requester, it.uuid) }
        .run { toResponse(meetingMemberApiService.getUsersByMeeting(uuid)) }

    fun getMeeting(meetingId: UUID) =
        findMeetingById(meetingId)
            .toResponse(meetingMemberApiService.getUsersByMeeting(meetingId))

    fun deleteById(requester: UUID, meetingId: UUID) =
        findMeetingById(meetingId)
            .checkOwner(requester)
            .also { meetingRepository.deleteById(meetingId) }

    fun editMeeting(requester: UUID, meetingId: UUID, request: CreateMeetingRequest) =
        findMeetingById(meetingId)
            .checkOwner(requester)
            .applyModifications(request)
            .toResponse(meetingMemberApiService.getUsersByMeeting(meetingId))

    fun getMeetings() = meetingRepository.findAll()
        .map { it.toResponse(meetingMemberApiService.getUsersByMeeting(it.id)) }

    private fun findMeetingById(meetingId: UUID) = meetingRepository
        .findById(meetingId)
        .orElseThrow { IllegalArgumentException("Meeting with id $meetingId not found") }

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

    fun getMeetingUrl(meetingId: UUID) = UrlResponse(meetingConfig.baseMeetingUrl + meetingId)
}
