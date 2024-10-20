package ru.ardyc.kairos.service

import org.springframework.stereotype.Service
import ru.ardyc.kairos.model.entity.Meeting
import ru.ardyc.kairos.model.request.CreateMeetingRequest
import ru.ardyc.kairos.repository.MeetingRepository
import java.util.UUID

@Service
class MeetingService(
    private val meetingRepository: MeetingRepository,
) {
    fun createMeeting(
        userId: UUID,
        request: CreateMeetingRequest,
    ) = meetingRepository.save(
        Meeting(
            uuid = UUID.randomUUID(),
            owner = userId,
            title = request.title,
            description = request.description,
            start = request.start,
            end = request.end,
        ),
    )

    fun getMeeting(meetingId: UUID): Meeting =
        meetingRepository
            .findById(meetingId)
            .orElseThrow { IllegalArgumentException("Meeting with id $meetingId not found") }

    fun deleteMeeting(
        userId: UUID,
        meetingId: UUID,
    ) {
        val meeting = getMeeting(meetingId)
        if (meeting.owner != userId) {
            throw IllegalArgumentException("Meeting with id $meetingId has not owner $userId")
        }
        meetingRepository.deleteById(meetingId)
    }
}
