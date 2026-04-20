package com.example.nudgapi.security

import com.example.nudgapi.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2AuthenticationSuccessHandler(
    private val jwtUtil: JwtUtil,
    private val userRepository: UserRepository,
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

        val user = userRepository.findByEmail(email) ?: run {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User not found after OAuth2 login")
            return
        }

        val token = jwtUtil.generateAccessToken(user.id!!)
        response.sendRedirect("http://localhost:3000/login?token=$token")
    }
}
