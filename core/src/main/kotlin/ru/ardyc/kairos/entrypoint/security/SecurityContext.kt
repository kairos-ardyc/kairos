package ru.ardyc.kairos.entrypoint.security

import ru.ardyc.tenshi.user.UserInfoResponse

object SecurityContextHolder {
    val securityContext = ThreadLocal<SecurityContext>()
    fun createContext(user: UserInfoResponse, jwtToken: String) {
        securityContext.set(SecurityContext(user, jwtToken))
    }
}

class SecurityContext(
    var userInfo: UserInfoResponse,
    var userJwt: String
)