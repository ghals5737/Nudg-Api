package com.example.nudgapi.dto

import com.example.nudgapi.domain.Routine
import java.time.Instant

data class RoutineResponse(
    val id: Long,
    val userId: Long,
    val title: String,
    val icon: String,
    val iconBg: String,
    val duration: Int,
    val time: String,
    val days: List<Boolean>,
    val active: Boolean,
    val smartReminders: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    companion object {
        fun from(r: Routine) = RoutineResponse(
            id = r.id!!,
            userId = r.userId,
            title = r.title,
            icon = r.icon,
            iconBg = r.iconBg,
            duration = r.duration,
            time = r.scheduledTime.toString().substring(0, 5),
            days = r.getDaysList(),
            active = r.active,
            smartReminders = r.smartReminders,
            createdAt = r.createdAt,
            updatedAt = r.updatedAt,
        )
    }
}

data class RoutineWithRhythmResponse(
    val id: Long,
    val userId: Long,
    val title: String,
    val icon: String,
    val iconBg: String,
    val duration: Int,
    val time: String,
    val days: List<Boolean>,
    val active: Boolean,
    val smartReminders: Boolean,
    val rhythm: List<Boolean>,
    val successRate: Int,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    companion object {
        fun from(r: Routine, rhythm: List<Boolean>, successRate: Int) = RoutineWithRhythmResponse(
            id = r.id!!,
            userId = r.userId,
            title = r.title,
            icon = r.icon,
            iconBg = r.iconBg,
            duration = r.duration,
            time = r.scheduledTime.toString().substring(0, 5),
            days = r.getDaysList(),
            active = r.active,
            smartReminders = r.smartReminders,
            rhythm = rhythm,
            successRate = successRate,
            createdAt = r.createdAt,
            updatedAt = r.updatedAt,
        )
    }
}

data class CreateRoutineRequest(
    val title: String,
    val icon: String? = null,
    val iconBg: String? = null,
    val duration: Int,
    val time: String,
    val days: List<Boolean>,
    val smartReminders: Boolean = true,
)

data class UpdateRoutineRequest(
    val title: String? = null,
    val icon: String? = null,
    val iconBg: String? = null,
    val duration: Int? = null,
    val time: String? = null,
    val days: List<Boolean>? = null,
    val active: Boolean? = null,
    val smartReminders: Boolean? = null,
)

data class RoutineLogRequest(val date: String)

data class RhythmEntry(val date: String, val completed: Boolean)

data class RoutineRhythmResponse(
    val routineId: Long,
    val rhythm: List<RhythmEntry>,
    val successRate: Int,
)
