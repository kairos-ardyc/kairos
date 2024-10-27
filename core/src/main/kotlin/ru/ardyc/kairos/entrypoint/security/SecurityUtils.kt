package ru.ardyc.kairos.entrypoint.security

import ru.ardyc.tenshi.user.UserInfoResponse
import java.util.UUID

fun <T> userContext(body: (UserInfoResponse) -> T) = body(user)

val UserInfoResponse.userId: UUID
    get() = UUID.fromString(uuid)