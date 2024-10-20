package ru.ardyc.kairos.entrypoint

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.ardyc.kairos.model.request.CreateMeetingRequest
import ru.ardyc.kairos.service.MeetingMemberService
import ru.ardyc.kairos.service.MeetingService
import ru.ardyc.kairos.service.TenshiService
import java.util.UUID

@RestController
@RequestMapping("/api/v1/meetings")
class MeetingController(
    private val meetingService: MeetingService,
    private val meetingMemberService: MeetingMemberService,
    private val tenshiService: TenshiService,
) {
    @PostMapping("/create")
    fun createMeeting(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody request: CreateMeetingRequest,
    ) = runCatching {
        val userId = extractUserId(authorization)
        meetingService.createMeeting(userId, request)
    }.getOrElse {
        throw IllegalStateException("Invalid request", it)
    }

    @GetMapping("/get/{meetingId}")
    fun getMeeting(
        @RequestHeader("Authorization") authorization: String,
        @PathVariable("meetingId") meetingId: String,
    ) = runCatching {
        val userId = extractUserId(authorization)
        val meeting = meetingService.getMeeting(UUID.fromString(meetingId))
        if (meetingMemberService.isMember(meeting.id, userId)) {
            meeting
        } else {
            null
        }
    }.getOrElse {
        throw IllegalStateException("Invalid request", it)
    }

    @GetMapping("/delete/{meetingId}")
    fun deleteMeeting(
        @RequestHeader("Authorization") authorization: String,
        @PathVariable("meetingId") meetingId: String,
    ) = runCatching {
        val userId = extractUserId(authorization)
        meetingService.deleteMeeting(userId, UUID.fromString(meetingId))
    }.getOrElse {
        throw IllegalStateException("Invalid request", it)
    }

    @GetMapping("/my")
    fun getMyMeetings(
        @RequestHeader("Authorization") authorization: String,
    ) = runCatching {
        val userId = extractUserId(authorization)
        meetingMemberService
            .getUserMeetings(userId)
            .map { meetingId -> meetingService.getMeeting(meetingId) }
    }.getOrElse {
        throw IllegalStateException("Invalid request", it)
    }

    @PostMapping("/join/{meetingId}")
    fun joinMeeting(
        @RequestHeader("Authorization") authorization: String,
        @PathVariable("meetingId") meetingId: String,
    ) = runCatching {
        val userId = extractUserId(authorization)
        meetingMemberService.joinMeeting(userId, UUID.fromString(meetingId))
    }.getOrElse {
        throw IllegalStateException("Invalid request", it)
    }

    @PostMapping("/leave/{meetingId}")
    fun leaveMeeting(
        @RequestHeader("Authorization") authorization: String,
        @PathVariable("meetingId") meetingId: String,
    ) = runCatching {
        val userId = extractUserId(authorization)
        meetingMemberService.leaveMeeting(userId, UUID.fromString(meetingId))
    }.getOrElse {
        throw IllegalStateException("Invalid request", it)
    }

    private fun extractUserId(authorization: String): UUID {
        val token = authorization.split(" ")[1]
        val userInfo = tenshiService.getUserByToken(token)
        return UUID.fromString(userInfo.uuid)
    }
}
