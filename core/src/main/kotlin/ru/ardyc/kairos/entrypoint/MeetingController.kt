package ru.ardyc.kairos.entrypoint

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.ardyc.kairos.entrypoint.security.user
import ru.ardyc.kairos.entrypoint.security.userId
import ru.ardyc.kairos.model.request.CreateMeetingRequest
import ru.ardyc.kairos.service.meeting.MeetingMemberService
import ru.ardyc.kairos.service.meeting.MeetingService
import java.util.UUID

@RestController
@RequestMapping("/api/v1/meetings")
@CrossOrigin
class MeetingController(
    private val meetingService: MeetingService,
    private val meetingMemberService: MeetingMemberService
) {

    @GetMapping
    fun getMeetings() = meetingService.getMeetings()

    @PostMapping
    fun createMeeting(@RequestBody request: CreateMeetingRequest) = meetingService.createMeeting(request)

    @PutMapping("/{meetingId}")
    fun editMeeting(@PathVariable("meetingId") meetingId: UUID, @RequestBody request: CreateMeetingRequest) =
        meetingService.editMeeting(meetingId, request)

    @GetMapping("/{meetingId}")
    fun getMeeting(@PathVariable("meetingId") meetingId: String) = run {
        val meeting = UUID.fromString(meetingId)
        if (meetingMemberService.isMember(meeting)) {
            meetingService.getMeeting(meeting)
        } else null
    }

    @GetMapping("/my")
    fun getMyMeetings() = meetingMemberService
        .getUserMeetings(user.userId)
        .map { meetingId -> meetingService.getMeeting(meetingId) }

}
