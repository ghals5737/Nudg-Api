package com.example.nudgapi.service.routines

import com.example.nudgapi.domain.routine.RoutineCompletionLog
import com.example.nudgapi.domain.routine.Routines
import com.example.nudgapi.dto.user.*
import com.example.nudgapi.repository.RoutineCompletionLogRepository
import com.example.nudgapi.repository.RoutinesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class RoutineService(
    private val routinesRepository: RoutinesRepository,
    private val routineCompletionLogRepository: RoutineCompletionLogRepository
) {
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")


    @Transactional
    fun createRoutine(request: CreateRoutineRequest, userId: Long): RoutineResponse {
        val routine = Routines(
            userId = userId,
            title = request.title,
            durationMinutes = request.duration,
            startTime = LocalTime.parse(request.time, timeFormatter),
            recurrenceRule = request.days.joinToString(","), // Simplification
            notificationType = request.notificationType,
            notificationMessage = request.notificationMessage,
            emoji = request.emoji,
            isActive = true
        )

        val savedRoutine = routinesRepository.save(routine)
        return RoutineResponse.from(savedRoutine)
    }

    @Transactional(readOnly = true)
    fun getRoutines(userId: Long): List<RoutineResponse> {
        val routines = routinesRepository.findByUserId(userId)
        return routines.map { RoutineResponse.from(it) }
    }

    @Transactional(readOnly = true)
    fun getRoutine(routineId: Long, userId: Long): RoutineDetailResponse {
        val routine = routinesRepository.findById(routineId).orElseThrow {
            IllegalArgumentException("Routine not found")
        }

        if (routine.userId != userId) {
            throw IllegalAccessException("User not authorized to view this routine")
        }

        return RoutineDetailResponse.fromDetail(routine)
    }

    @Transactional
    fun updateRoutine(routineId: Long, request: UpdateRoutineRequest, userId: Long): RoutineDetailResponse {
        val routine = routinesRepository.findById(routineId).orElseThrow {
            IllegalArgumentException("Routine not found")
        }

        if (routine.userId != userId) {
            throw IllegalAccessException("User not authorized to update this routine")
        }

        request.title?.let { routine.title = it }
        request.duration?.let { routine.durationMinutes = it }
        request.time?.let { routine.startTime = LocalTime.parse(it, timeFormatter) }
        request.days?.let { routine.recurrenceRule = it.joinToString(",") }
        request.notificationType?.let { routine.notificationType = it }
        request.notificationMessage?.let { routine.notificationMessage = it }
        request.emoji?.let { routine.emoji = it }

        val updatedRoutine = routinesRepository.save(routine)
        return RoutineDetailResponse.fromDetail(updatedRoutine)
    }

    @Transactional
    fun deleteRoutine(routineId: Long, userId: Long) {
        val routine = routinesRepository.findById(routineId).orElseThrow {
            IllegalArgumentException("Routine not found")
        }

        if (routine.userId != userId) {
            throw IllegalAccessException("User not authorized to delete this routine")
        }

        routinesRepository.delete(routine)
    }

    @Transactional
    fun updateRoutineStatus(routineId: Long, active: Boolean, userId: Long): RoutineDetailResponse {
        val routine = routinesRepository.findById(routineId).orElseThrow {
            IllegalArgumentException("Routine not found")
        }

        if (routine.userId != userId) {
            throw IllegalAccessException("User not authorized to update this routine")
        }

        routine.isActive = active
        val updatedRoutine = routinesRepository.save(routine)
        return RoutineDetailResponse.fromDetail(updatedRoutine)
    }

    @Transactional
    fun completeRoutine(routineId: Long, date: String?, userId: Long): CompleteRoutineResponse {
        val routine = routinesRepository.findById(routineId).orElseThrow {
            IllegalArgumentException("Routine not found")
        }

        if (routine.userId != userId) {
            throw IllegalAccessException("User not authorized to complete this routine")
        }

        val completionDate = if (date != null) LocalDate.parse(date, dateFormatter) else LocalDate.now()

        val log = RoutineCompletionLog(
            userId = userId,
            routineId = routineId,
            completionDate = completionDate
        )
        routineCompletionLogRepository.save(log)

        // TODO: Calculate actual weekly progress
        val weeklyProgress = listOf(1, 1, 1, 1, 0, 1, 1)

        return CompleteRoutineResponse(
            success = true,
            message = "Routine completed successfully",
            weeklyProgress = weeklyProgress
        )
    }

    @Transactional
    fun snoozeRoutine(routineId: Long, minutes: Int, userId: Long): SuccessResponse {
        val routine = routinesRepository.findById(routineId).orElseThrow {
            IllegalArgumentException("Routine not found")
        }

        if (routine.userId != userId) {
            throw IllegalAccessException("User not authorized to snooze this routine")
        }

        // TODO: Implement actual snooze logic
        return SuccessResponse(success = true, message = "Routine snoozed for $minutes minutes")
    }
}
