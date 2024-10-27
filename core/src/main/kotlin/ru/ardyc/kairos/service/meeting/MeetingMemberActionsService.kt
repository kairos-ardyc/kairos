package ru.ardyc.kairos.service.meeting

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MeetingMemberActionsService(
    private val meetingMemberApiService: MeetingMemberApiService,
    private val meetingApiService: MeetingApiService
) {

    fun addMemberToMeeting(userId: UUID, meetingId: UUID) =
        meetingMemberApiService.addUserToMeeting(userId, meetingId)

    fun removeMemberFromMeeting(userId: UUID, meetingId: UUID) = with(meetingApiService.getMeeting(meetingId)) {
        if (owner == userId) {
            meetingMemberApiService.removeAllUsers(meetingId)
            meetingApiService.deleteById(owner, meetingId)
        }
        meetingMemberApiService.removeUserFromMeeting(userId, meetingId)
    }
}