package com.example.nudgapi.dto

data class UpdateProfileRequest(
    val name: String? = null,
    val avatarUrl: String? = null,
)

data class UpdateSettingsRequest(
    val theme: String? = null,
    val language: String? = null,
    val timezone: String? = null,
    val notificationsEnabled: Boolean? = null,
)
