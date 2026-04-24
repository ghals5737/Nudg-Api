package com.example.nudgapi.dto

import com.example.nudgapi.domain.User
import java.time.Instant

data class SignupRequest(val name: String, val email: String, val password: String)
data class LoginRequest(val email: String, val password: String)

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val avatarUrl: String?,
    val timezone: String,
    val language: String,
    val theme: String,
    val notificationsEnabled: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    companion object {
        fun from(u: User) = UserResponse(
            id = u.id!!,
            name = u.name,
            email = u.email,
            avatarUrl = u.avatarUrl,
            timezone = u.timezone,
            language = u.language,
            theme = u.theme.name,
            notificationsEnabled = u.notificationsEnabled,
            createdAt = u.createdAt,
            updatedAt = u.updatedAt,
        )
    }
}

data class AuthResponse(val user: UserResponse, val accessToken: String, val refreshToken: String)
data class RefreshRequest(val refreshToken: String)
