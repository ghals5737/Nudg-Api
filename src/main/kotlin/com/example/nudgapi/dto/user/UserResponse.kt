package com.example.nudgapi.dto.user

data class UserResponse(
    val id: Long?,
    val email: String,
    val provider: String,
    val providerId: String?,
    val profileImage: String?,
    val name: String,
    val age: Int?, // age is not in the entity, so it's nullable
    val gender: String?, // gender is not in the entity, so it's nullable
    val cbtRecords: List<CbtRecordDto>,
    val goals: List<GoalDto>,
    val routines: List<RoutineDto>,
    val schedules: List<ScheduleDto>
)
