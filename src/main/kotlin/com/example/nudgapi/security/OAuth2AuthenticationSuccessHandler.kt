package com.example.nudgapi.security

import com.example.nudgapi.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class OAuth2AuthenticationSuccessHandler(
    @Lazy private val authService: AuthService,
    @Value("\${app.frontend-url}") private val frontendUrl: String,
) : SimpleUrlAuthenticationSuccessHandler() {

    private val log = LoggerFactory.getLogger(javaClass)

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

        val redirectUrl = UriComponentsBuilder
            .fromUriString("${frontendUrl.trimEnd('/')}/login")
            .queryParam("token", authResponse.accessToken)
            .queryParam("refreshToken", authResponse.refreshToken)
            .build()
            .toUriString()

        log.info(
            "OAuth2 success redirect: base={}, hasToken={}, hasRefresh={}, totalLen={}",
            frontendUrl,
            authResponse.accessToken.isNotEmpty(),
            authResponse.refreshToken.isNotEmpty(),
            redirectUrl.length,
        )

        response.sendRedirect(redirectUrl)
    }
}
