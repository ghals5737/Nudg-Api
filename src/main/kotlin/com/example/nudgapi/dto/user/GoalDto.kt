package com.example.nudgapi.dto.user

import java.time.LocalDate
import java.time.LocalDateTime

data class GoalDto(
    val id: Long?,
    val userId: Long,
    val title: String,
    val description: String?,
    val tags: List<String>,
    val status: String?,
    val startDate: LocalDateTime?,
    val dueDate: LocalDate?
)
