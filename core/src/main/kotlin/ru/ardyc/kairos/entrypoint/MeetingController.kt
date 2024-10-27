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
import ru.ardyc.kairos.entrypoint.security.userContext
import ru.ardyc.kairos.entrypoint.security.userId
import ru.ardyc.kairos.model.request.CreateMeetingRequest
import ru.ardyc.kairos.service.meeting.MeetingApiService
import ru.ardyc.kairos.service.meeting.MeetingMemberApiService
import java.util.UUID

@RestController
@RequestMapping("/api/v1/meetings")
@CrossOrigin
class MeetingController(
    private val meetingApiService: MeetingApiService,
    private val meetingMemberApiService: MeetingMemberApiService
) {

    @GetMapping
    fun getMeetings() = userContext {
        meetingApiService.getMeetings()
    }

    @PostMapping
    fun createMeeting(@RequestBody request: CreateMeetingRequest) = userContext { requester ->
        meetingApiService.createMeeting(requester.userId, request)
    }

    @PutMapping("/{meetingId}")
    fun editMeeting(
        @PathVariable("meetingId") meetingId: UUID,
        @RequestBody request: CreateMeetingRequest
    ) = userContext { requester ->
        meetingApiService.editMeeting(requester.userId, meetingId, request)
    }

    @GetMapping("/{meetingId}")
    fun getMeeting(@PathVariable("meetingId") meetingId: String) = userContext { user ->
        val meeting = UUID.fromString(meetingId)
        if (meetingMemberApiService.isMember(user.userId, meeting)) {
            meetingApiService.getMeeting(meeting)
        } else null
    }

    @GetMapping("/{meetingId}/url")
    fun getMeetingUrl(@PathVariable("meetingId") meetingId: String) = userContext {
        meetingApiService.getMeetingUrl(UUID.fromString(meetingId))
    }

    @GetMapping("/my")
    fun getMyMeetings() = userContext {
        meetingMemberApiService
            .getUserMeetings(user.userId)
            .map { meetingId -> meetingApiService.getMeeting(meetingId) }
    }

}
