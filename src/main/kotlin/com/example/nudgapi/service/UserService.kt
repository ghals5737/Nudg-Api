package com.example.nudgapi.service

import com.example.nudgapi.domain.ThemeEnum
import com.example.nudgapi.dto.*
import com.example.nudgapi.exception.BadRequestException
import com.example.nudgapi.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val repo: UserRepository) {

    @Transactional(readOnly = true)
    fun getProfile(userId: Long) =
        UserResponse.from(repo.findById(userId).orElseThrow { BadRequestException("User not found") })

    @Transactional
    fun updateProfile(userId: Long, req: UpdateProfileRequest): UserResponse {
        val user = repo.findById(userId).orElseThrow { BadRequestException("User not found") }
        req.name?.let { user.name = it }; req.avatarUrl?.let { user.avatarUrl = it }
        return UserResponse.from(repo.save(user))
    }

    @Transactional
    fun updateSettings(userId: Long, req: UpdateSettingsRequest): UserResponse {
        val user = repo.findById(userId).orElseThrow { BadRequestException("User not found") }
        req.theme?.let { user.theme = ThemeEnum.valueOf(it) }
        req.language?.let { user.language = it }
        req.timezone?.let { user.timezone = it }
        req.notificationsEnabled?.let { user.notificationsEnabled = it }
        return UserResponse.from(repo.save(user))
    }
}
