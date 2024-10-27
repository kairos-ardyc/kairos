package ru.ardyc.kairos.entrypoint

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.ardyc.kairos.service.meeting.MeetingMemberActionsService
import java.util.UUID


@RestController
@CrossOrigin
@RequestMapping("/api/v1/meetings")
class MeetingMembersActionsController(
    private val meetingMemberActionsService: MeetingMemberActionsService
) {

    @PostMapping("/{meetingId}/join")
    fun joinMeeting(@PathVariable("meetingId") meetingId: UUID) =
        meetingMemberActionsService.joinMeeting(meetingId)

    @DeleteMapping("/{meetingId}/leave")
    fun leaveMeeting(@PathVariable("meetingId") meetingId: UUID) =
        meetingMemberActionsService.leaveMeeting(meetingId)

}