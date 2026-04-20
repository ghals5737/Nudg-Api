package com.example.nudgapi.dto

import com.example.nudgapi.domain.ScheduleBlock
import java.time.Instant
import java.time.LocalDate

data class ScheduleBlockResponse(
    val id: Long,
    val userId: Long,
    val title: String,
    val date: LocalDate,
    val startTime: Double,
    val endTime: Double,
    val duration: Double,
    val location: String?,
    val tag: String?,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    companion object {
        fun from(b: ScheduleBlock) = ScheduleBlockResponse(
            id = b.id!!,
            userId = b.userId,
            title = b.title,
            date = b.blockDate,
            startTime = b.startTime,
            endTime = b.endTime,
            duration = b.duration,
            location = b.location,
            tag = b.tag?.name,
            status = b.status.name,
            createdAt = b.createdAt,
            updatedAt = b.updatedAt,
        )
    }
}

data class CreateScheduleBlockRequest(
    val title: String,
    val date: LocalDate,
    val startTime: Double,
    val endTime: Double,
    val duration: Double,
    val location: String? = null,
    val tag: String? = null,
)

data class UpdateScheduleBlockRequest(
    val title: String? = null,
    val date: LocalDate? = null,
    val startTime: Double? = null,
    val endTime: Double? = null,
    val duration: Double? = null,
    val location: String? = null,
    val tag: String? = null,
    val status: String? = null,
)

data class SnoozeRequest(val minutes: Int)
