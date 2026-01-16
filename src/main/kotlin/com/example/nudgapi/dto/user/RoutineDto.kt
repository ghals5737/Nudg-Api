package com.example.nudgapi.dto.user

import com.example.nudgapi.domain.routine.Routines
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class CreateRoutineRequest(
    val title: String,
    val duration: Int,
    val time: String,
    val frequency: String,
    val days: List<String>,
    val alarmEnabled: Boolean,
    val notificationType: String,
    val notificationMessage: String,
    val emoji: String
)

data class UpdateRoutineRequest(
    val title: String?,
    val duration: Int?,
    val time: String?,
    val frequency: String?,
    val days: List<String>?,
    val alarmEnabled: Boolean?,
    val notificationType: String?,
    val notificationMessage: String?,
    val emoji: String?
)

data class UpdateRoutineStatusRequest(
    val active: Boolean
)

data class CompleteRoutineRequest(
    val date: String?
)

data class CompleteRoutineResponse(
    val success: Boolean,
    val message: String,
    val weeklyProgress: List<Int>
)

data class SnoozeRoutineRequest(
    val minutes: Int
)

data class SuccessResponse(
    val success: Boolean,
    val message: String
)

data class RoutineResponse(
    val id: Long?,
    val title: String,
    val duration: Int?,
    val time: String?,
    val days: List<String>,
    val frequency: String?,
    val active: Boolean?,
    val createdAt: String?,
    val updatedAt: String?
) {
    companion object {
        private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        fun from(routine: Routines): RoutineResponse {
            return RoutineResponse(
                id = routine.id,
                title = routine.title,
                duration = routine.durationMinutes,
                time = routine.startTime?.format(timeFormatter),
                days = routine.recurrenceRule?.split(",") ?: emptyList(), // This is a simplification
                frequency = "매일", // This is a placeholder
                active = routine.isActive,
                createdAt = routine.createdDate.toString(),
                updatedAt = routine.updatedDate.toString()
            )
        }
    }
}

data class RoutineDetailResponse(
    val id: Long?,
    val title: String,
    val duration: Int?,
    val time: String?,
    val days: List<String>,
    val frequency: String?,
    val notificationType: String?,
    val notificationMessage: String?,
    val emoji: String?,
    val active: Boolean?,
    val weeklyProgress: List<Int>,
    val completionHistory: List<CompletionHistoryItem>,
    val createdAt: String?,
    val updatedAt: String?
) {
    companion object {
        private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        fun fromDetail(routine: Routines): RoutineDetailResponse {
            return RoutineDetailResponse(
                id = routine.id,
                title = routine.title,
                duration = routine.durationMinutes,
                time = routine.startTime?.format(timeFormatter),
                days = routine.recurrenceRule?.split(",") ?: emptyList(), // Simplification
                frequency = "매일", // Placeholder
                notificationType = routine.notificationType,
                notificationMessage = routine.notificationMessage,
                emoji = routine.emoji,
                active = routine.isActive,
                weeklyProgress = listOf(1, 1, 0, 1, 0, 1, 1), // Placeholder
                completionHistory = listOf( // Placeholder
                    CompletionHistoryItem(date = "2025-12-10", completed = true),
                    CompletionHistoryItem(date = "2025-12-11", completed = false)
                ),
                createdAt = routine.createdDate.toString(),
                updatedAt = routine.updatedDate.toString()
            )
        }
    }
}

data class CompletionHistoryItem(
    val date: String,
    val completed: Boolean
)

// [추가] UserService에서 사용하는 DTO 정의
data class RoutineDto(
    val id: Long?,
    val userId: Long,
    val name: String,
    val days: List<String>,
    val tasks: List<TaskDto>
)

// [추가] RoutineDto 내부에서 사용하는 TaskDto 정의
data class TaskDto(
    val id: Long?,
    val name: String,
    val isCompleted: Boolean
)
