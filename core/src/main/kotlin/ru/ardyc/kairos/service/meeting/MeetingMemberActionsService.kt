package ru.ardyc.kairos.service.meeting

import org.springframework.stereotype.Service
import ru.ardyc.kairos.entrypoint.security.userContext
import ru.ardyc.kairos.entrypoint.security.userId
import java.util.UUID

@Service
class MeetingMemberActionsService(
    private val meetingMemberService: MeetingMemberService,
    private val meetingService: MeetingService
) {

    fun leaveMeeting(meetingId: UUID) = userContext { user ->
        with(meetingService.getMeeting(meetingId)) {
            if (owner == user.userId) {
                meetingMemberService.removeAllUsers(meetingId)
                meetingService.deleteById(meetingId)
            }
            meetingMemberService.removeUserFromMeeting(user.userId, meetingId)
        }
    }


    fun joinMeeting(meetingId: UUID) = userContext { user ->
        meetingMemberService.addUserToMeeting(user.userId, meetingId)
    }
}