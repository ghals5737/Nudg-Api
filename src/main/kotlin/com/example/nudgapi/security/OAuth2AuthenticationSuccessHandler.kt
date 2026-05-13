package com.example.nudgapi.security

import com.example.nudgapi.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2AuthenticationSuccessHandler(
    @Lazy private val authService: AuthService,
    @Value("\${app.frontend-url}") private val frontendUrl: String,
) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        val oidcUser = authentication.principal as OidcUser
        val email = oidcUser.email ?: run {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email not available")
            return
        }

        val authResponse = authService.buildAuthResponseByEmail(email) ?: run {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User not found after OAuth2 login")
            return
        }

        val base = frontendUrl.trimEnd('/')
        response.sendRedirect(
            "$base/login?token=${authResponse.accessToken}&refreshToken=${authResponse.refreshToken}"
        )
    }
}
