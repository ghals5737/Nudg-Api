package com.example.nudgapi.service.users

import com.example.nudgapi.dto.user.*
import com.example.nudgapi.repository.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val cbtRecordRepository: CbtRecordRepository,
    private val goalsRepository: GoalsRepository,
    private val goalTagRepository: GoalTagRepository,
    private val routinesRepository: RoutinesRepository,
    private val taskRepository: TaskRepository,
    private val scheduleRepository: ScheduleRepository
) {

    @Transactional(readOnly = true)
    fun getUser(userId: Long): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw RuntimeException("User not found")

        val cbtRecords = cbtRecordRepository.findByUserId(userId).map {
            CbtRecordDto(
                id = it.id,
                userId = it.userId,
                recordedAt = it.recordedAt,
                moodScore = it.moodScore,
                moodLabel = it.moodLabel,
                emoji = it.emoji,
                impulse = it.impulse,
                copingMethod = it.copingMethod,
                location = it.location,
                resultStatus = it.resultStatus,
                notes = it.notes
            )
        }

        val goals = goalsRepository.findByUserId(userId).map { goal ->
            val tags: List<String> = goal.id?.let { goalId ->
                goalTagRepository.findByGoalId(goalId).map { it.tagName }
            } ?: emptyList()

            GoalDto(
                id = goal.id,
                userId = goal.userId,
                title = goal.title,
                description = goal.description,
                tags = tags,
                status = goal.status,
                startDate = goal.createdDate,
                dueDate = goal.dueDate
            )
        }

        val routines = routinesRepository.findByUserId(userId).map { routine ->
            val tasks = routine.id?.let { routineId ->
                taskRepository.findByRoutineId(routineId).map { task ->
                    TaskDto(
                        id = task.id,
                        name = task.title,
                        isCompleted = task.completed
                    )
                }
            } ?: emptyList()

            val days = mutableListOf<String>()
            routine.recurrenceRule?.let {
                if (it.startsWith("RRULE:FREQ=WEEKLY;BYDAY=")) {
                    val daysStr = it.substring("RRULE:FREQ=WEEKLY;BYDAY=".length)
                    days.addAll(daysStr.split(","))
                }
            }

            RoutineDto(
                id = routine.id,
                userId = routine.userId,
                name = routine.title,
                days = days,
                tasks = tasks
            )
        }

        val schedules = scheduleRepository.findByUserId(userId).map {
            ScheduleDto(
                id = it.id,
                userId = it.userId,
                title = it.title,
                description = it.title,
                isCompleted = false, // Schedule entity does not have a 'completed' status
                isAllDay = false,
                startDateTime = it.date.atTime(it.startTime),
                endDateTime = it.date.atTime(it.endTime)
            )
        }

        return UserResponse(
            id = user.id,
            email = user.email,
            provider = user.provider,
            providerId = user.providerId,
            profileImage = user.profileImage,
            name = user.name,
            age = null, // Not in entity
            gender = null, // Not in entity
            cbtRecords = cbtRecords,
            goals = goals,
            routines = routines,
            schedules = schedules
        )
    }
}
