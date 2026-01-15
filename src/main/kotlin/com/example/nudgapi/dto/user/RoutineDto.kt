package com.example.nudgapi.dto.user

data class RoutineDto(
    val id: Long?,
    val userId: Long,
    val name: String,
    val days: List<String>,
    val tasks: List<TaskDto>
)
