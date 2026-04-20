package com.example.nudgapi.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(private val jwtUtil: JwtUtil) : OncePerRequestFilter() {

    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        val token = req.getHeader("Authorization")?.takeIf { it.startsWith("Bearer ") }?.substring(7)
        if (token != null && jwtUtil.isValid(token)) {
            val userId = jwtUtil.extractUserId(token)
            if (userId != null) {
                val principal = AuthPrincipal(userId, "")
                SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken(principal, null, principal.authorities)
            }
        }
        chain.doFilter(req, res)
    }
}
