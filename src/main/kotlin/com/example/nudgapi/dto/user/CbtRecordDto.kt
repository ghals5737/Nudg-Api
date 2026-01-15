package com.example.nudgapi.dto.user

import java.time.LocalDateTime

data class CbtRecordDto(
    val id: Long?,
    val userId: Long,
    val recordedAt: LocalDateTime?,
    val moodScore: Int,
    val moodLabel: String?,
    val emoji: String?,
    val impulse: String?,
    val copingMethod: String?,
    val location: String?,
    val resultStatus: String?,
    val notes: String?
)
