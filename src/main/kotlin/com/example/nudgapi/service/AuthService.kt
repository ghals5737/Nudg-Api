package com.example.nudgapi.service

import com.example.nudgapi.domain.RefreshToken
import com.example.nudgapi.domain.User
import com.example.nudgapi.dto.AuthResponse
import com.example.nudgapi.dto.LoginRequest
import com.example.nudgapi.dto.RefreshRequest
import com.example.nudgapi.dto.SignupRequest
import com.example.nudgapi.dto.UserResponse
import com.example.nudgapi.exception.BadRequestException
import com.example.nudgapi.exception.ConflictException
import com.example.nudgapi.repository.RefreshTokenRepository
import com.example.nudgapi.repository.UserRepository
import com.example.nudgapi.security.JwtUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
    @Value("\${jwt.refresh-token-expiry}") private val refreshTokenExpiry: Long,
) {

    @Transactional
    fun signup(req: SignupRequest): AuthResponse {
        if (userRepository.existsByEmail(req.email)) throw ConflictException("Email already in use")
        val user = userRepository.save(
            User(name = req.name, email = req.email, passwordHash = passwordEncoder.encode(req.password)!!)
        )
        return buildAuthResponse(user)
    }

    @Transactional
    fun login(req: LoginRequest): AuthResponse {
        val user = userRepository.findByEmail(req.email)
            ?: throw BadRequestException("Invalid email or password")
        if (!passwordEncoder.matches(req.password, user.passwordHash))
            throw BadRequestException("Invalid email or password")
        return buildAuthResponse(user)
    }

    @Transactional
    fun logout(userId: Long) = refreshTokenRepository.revokeAllByUserId(userId)

    @Transactional
    fun refresh(req: RefreshRequest): AuthResponse {
        val token = refreshTokenRepository.findByTokenHashAndRevokedFalse(req.refreshToken)
            ?: throw BadRequestException("Invalid or expired refresh token")
        if (token.expiresAt.isBefore(Instant.now())) {
            token.revoked = true
            refreshTokenRepository.save(token)
            throw BadRequestException("Refresh token expired")
        }
        token.revoked = true
        refreshTokenRepository.save(token)
        val user = userRepository.findById(token.userId).orElseThrow { BadRequestException("User not found") }
        return buildAuthResponse(user)
    }

    @Transactional(readOnly = true)
    fun me(userId: Long): UserResponse =
        UserResponse.from(userRepository.findById(userId).orElseThrow { BadRequestException("User not found") })

    fun buildAuthResponseByEmail(email: String): AuthResponse? {
        val user = userRepository.findByEmail(email) ?: return null
        return buildAuthResponse(user)
    }

    private fun buildAuthResponse(user: User): AuthResponse {
        val uid = user.id!!
        val accessToken = jwtUtil.generateAccessToken(uid)
        val rawToken = UUID.randomUUID().toString()
        refreshTokenRepository.save(
            RefreshToken(
                userId = uid,
                tokenHash = rawToken,
                expiresAt = Instant.now().plusSeconds(refreshTokenExpiry),
            )
        )
        return AuthResponse(user = UserResponse.from(user), accessToken = accessToken, refreshToken = rawToken)
    }
}
