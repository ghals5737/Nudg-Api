package com.example.nudgapi.service.schedules

import com.example.nudgapi.domain.schedule.Schedule
import com.example.nudgapi.dto.user.CreateScheduleRequest
import com.example.nudgapi.dto.user.ScheduleResponse
import com.example.nudgapi.dto.user.ScheduleDetailResponse
import com.example.nudgapi.dto.user.UpdateScheduleRequest
import com.example.nudgapi.repository.ScheduleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class ScheduleService(
    private val scheduleRepository: ScheduleRepository
) {
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @Transactional
    fun createSchedule(request: CreateScheduleRequest, userId: Long): ScheduleResponse {
        val startTime = LocalTime.parse(request.startTime, timeFormatter)
        val endTime = startTime.plusMinutes(request.duration)
        val date = LocalDate.parse(request.date, dateFormatter)

        val schedule = Schedule(
            userId = userId,
            title = request.title,
            date = date,
            startTime = startTime,
            endTime = endTime,
            color = request.color,
            template = request.template,
            notes = request.notes
        )

        val savedSchedule = scheduleRepository.save(schedule)
        return ScheduleResponse.from(savedSchedule)
    }

    @Transactional(readOnly = true)
    fun getSchedules(userId: Long): List<ScheduleResponse> {
        val schedules = scheduleRepository.findByUserId(userId)
        return schedules.map { ScheduleResponse.from(it) }
    }

    @Transactional(readOnly = true)
    fun getSchedule(scheduleId: Long, userId: Long): ScheduleDetailResponse {
        val schedule = scheduleRepository.findById(scheduleId).orElseThrow {
            IllegalArgumentException("Schedule not found")
        }

        if (schedule.userId != userId) {
            throw IllegalAccessException("User not authorized to view this schedule")
        }

        return ScheduleDetailResponse.from(schedule)
    }

    @Transactional
    fun updateSchedule(scheduleId: Long, request: UpdateScheduleRequest, userId: Long): ScheduleDetailResponse {
        val schedule = scheduleRepository.findById(scheduleId).orElseThrow {
            IllegalArgumentException("Schedule not found")
        }

        if (schedule.userId != userId) {
            throw IllegalAccessException("User not authorized to update this schedule")
        }

        request.title?.let { schedule.title = it }
        request.date?.let { schedule.date = LocalDate.parse(it, dateFormatter) }
        request.color?.let { schedule.color = it }
        request.template?.let { schedule.template = it }
        request.notes?.let { schedule.notes = it }

        if (request.startTime != null || request.duration != null) {
            val startTime = if (request.startTime != null) LocalTime.parse(request.startTime, timeFormatter) else schedule.startTime
            val duration = request.duration ?: java.time.Duration.between(schedule.startTime, schedule.endTime).toMinutes()
            schedule.startTime = startTime
            schedule.endTime = startTime.plusMinutes(duration)
        }

        val updatedSchedule = scheduleRepository.save(schedule)
        return ScheduleDetailResponse.from(updatedSchedule)
    }
}
