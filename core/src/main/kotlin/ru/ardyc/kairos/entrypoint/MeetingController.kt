package ru.ardyc.kairos.entrypoint

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.ardyc.kairos.entrypoint.security.SecurityContextHolder.securityContext
import ru.ardyc.kairos.model.request.CreateMeetingRequest
import ru.ardyc.kairos.service.MeetingMemberService
import ru.ardyc.kairos.service.MeetingService
import ru.ardyc.tenshi.user.UserInfoResponse
import java.util.UUID

@RestController
@RequestMapping("/api/v1/meetings")
@CrossOrigin
class MeetingController(
    private val meetingService: MeetingService,
    private val meetingMemberService: MeetingMemberService
) {
    @PostMapping("/create")
    fun createMeeting(@RequestBody request: CreateMeetingRequest) = userContext { user ->
        meetingService.createMeeting(user.userId, request)
    }

    @GetMapping("/{meetingId}")
    fun getMeeting(@PathVariable("meetingId") meetingId: String) = userContext { user ->
        val meeting = UUID.fromString(meetingId)
        if (meetingMemberService.isMember(meeting, user.userId)) {
            meetingService.getMeeting(meeting)
        } else null
    }

    @GetMapping("/my")
    fun getMyMeetings() = userContext { user ->
        meetingMemberService
            .getUserMeetings(user.userId)
            .map { meetingId -> meetingService.getMeeting(meetingId) }
    }

    @PostMapping("/{meetingId}/join")
    fun joinMeeting(@PathVariable("meetingId") meetingId: String) = userContext { user ->
        meetingMemberService.joinMeeting(user.userId, UUID.fromString(meetingId))
    }

    @DeleteMapping("/{meetingId}/leave")
    fun leaveMeeting(@PathVariable("meetingId") meetingId: String) = userContext { user ->
        meetingService.leaveMeeting(user.userId, UUID.fromString(meetingId))
    }

    private fun <T> userContext(body: (UserInfoResponse) -> T) =
        body(securityContext.get()?.userInfo ?: throw IllegalArgumentException("User not authenticated"))


    private val UserInfoResponse.userId: UUID
        get() = UUID.fromString(uuid)
}
