package ru.ardyc.kairos.mapper

import ru.ardyc.kairos.model.entity.Meeting
import ru.ardyc.kairos.model.entity.MeetingMember
import ru.ardyc.kairos.model.response.MeetingMemberResponse
import ru.ardyc.kairos.model.response.MeetingResponse
import ru.ardyc.tenshi.user.UserInfoResponse
import java.util.UUID

fun Meeting.toResponse(users: List<MeetingMemberResponse>) = MeetingResponse(
    uuid = uuid,
    owner = owner,
    title = title,
    room = room,
    description = description,
    start = start,
    end = end,
    members = users
)


fun MeetingMember.toResponse(info: UserInfoResponse) = MeetingMemberResponse(
    uuid = UUID.fromString(info.uuid),
    firstName = info.firstName,
    lastName = info.lastName,
    login = info.login
)