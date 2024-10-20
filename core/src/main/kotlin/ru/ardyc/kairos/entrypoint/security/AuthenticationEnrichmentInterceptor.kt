package ru.ardyc.kairos.entrypoint.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import ru.ardyc.kairos.entrypoint.security.SecurityContextHolder.createContext
import ru.ardyc.kairos.service.TenshiService

@Component
class AuthenticationEnrichmentInterceptor(
    private val tenshiService: TenshiService
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean =
        runCatching {
            if (request.getHeader("Authorization") != null) {
                val token = request.getHeader("Authorization").replace("Bearer ", "")
                createContext(tenshiService.getUserByToken(token), token)
            }
            return super.preHandle(request, response, handler)
        }.getOrElse {
            response.status = HttpStatus.UNAUTHORIZED.value()
            false
        }
}