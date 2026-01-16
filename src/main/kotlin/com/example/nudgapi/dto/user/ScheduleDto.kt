package com.example.nudgapi.dto.user

import com.example.nudgapi.domain.schedule.Schedule
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class CreateScheduleRequest(
    val title: String,
    val startTime: String,
    val duration: Long,
    val date: String,
    val color: String?,
    val template: String?,
    val notes: String?
)

data class UpdateScheduleRequest(
    val title: String?,
    val startTime: String?,
    val duration: Long?,
    val date: String?,
    val color: String?,
    val template: String?,
    val notes: String?
)

data class ScheduleResponse(
    val id: Long?,
    val title: String,
    val startTime: String,
    val endTime: String,
    val duration: Long,
    val date: String,
    val color: String?,
    val createdAt: String?,
    val updatedAt: String?
) {
    companion object {
        fun from(schedule: Schedule): ScheduleResponse {
            val duration = Duration.between(schedule.startTime, schedule.endTime).toMinutes()
            return ScheduleResponse(
                id = schedule.id,
                title = schedule.title,
                startTime = schedule.startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                endTime = schedule.endTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                duration = duration,
                date = schedule.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                color = schedule.color,
                createdAt = schedule.createdDate.toString(),
                updatedAt = schedule.updatedDate.toString()
            )
        }
    }
}

data class ScheduleDetailResponse(
    val id: Long?,
    val title: String,
    val startTime: String,
    val endTime: String,
    val duration: Long,
    val date: String,
    val color: String?,
    val template: String?,
    val notes: String?,
    val createdAt: String?,
    val updatedAt: String?
) {
    companion object {
        private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        fun from(schedule: Schedule): ScheduleDetailResponse {
            val duration = Duration.between(schedule.startTime, schedule.endTime).toMinutes()
            return ScheduleDetailResponse(
                id = schedule.id,
                title = schedule.title,
                startTime = schedule.startTime.format(timeFormatter),
                endTime = schedule.endTime.format(timeFormatter),
                duration = duration,
                date = schedule.date.format(dateFormatter),
                color = schedule.color,
                template = schedule.template,
                notes = schedule.notes,
                createdAt = schedule.createdDate.toString(),
                updatedAt = schedule.updatedDate.toString()
            )
        }
    }
}

// [추가] UserService에서 사용하는 DTO 정의
data class ScheduleDto(
    val id: Long?,
    val userId: Long,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
    val isAllDay: Boolean,
    val startDateTime: java.time.LocalDateTime?,
    val endDateTime: java.time.LocalDateTime?
)
