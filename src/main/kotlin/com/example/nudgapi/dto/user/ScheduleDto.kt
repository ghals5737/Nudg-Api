package com.example.nudgapi.dto.user

import java.time.LocalDateTime

data class ScheduleDto(
    val id: Long?,
    val userId: Long,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
    val isAllDay: Boolean,
    val startDateTime: LocalDateTime?,
    val endDateTime: LocalDateTime?
)
