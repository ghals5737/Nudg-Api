package com.example.nudgapi.service

import com.example.nudgapi.dto.*
import com.example.nudgapi.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime

@Service
class DashboardService(
    private val scheduleBlockRepository: ScheduleBlockRepository,
    private val routineRepository: RoutineRepository,
    private val routineLogRepository: RoutineLogRepository,
    private val cbtEntryRepository: CbtEntryRepository,
    private val cbtEntryTagRepository: CbtEntryTagRepository,
) {

    @Transactional(readOnly = true)
    fun getDashboard(userId: Long): DashboardResponse {
        val today = LocalDate.now()
        val now = LocalTime.now()
        val dayOfWeekIdx = today.dayOfWeek.value - 1

        val todayBlocks = scheduleBlockRepository
            .findAllByUserIdAndBlockDateOrderByStartTimeAsc(userId, today)
            .map { ScheduleBlockResponse.from(it) }

        val nowDecimal = now.hour + now.minute / 60.0
        val focusTask = todayBlocks.firstOrNull { it.startTime >= nowDecimal } ?: todayBlocks.lastOrNull()

        val routines = routineRepository.findAllByUserIdOrderByCreatedAtAsc(userId)
        val todayLoggedIds = routineLogRepository.findAllByUserIdAndLogDate(userId, today).map { it.routineId }.toSet()

        val todayRoutines = routines
            .filter { r -> r.active && r.getDaysList().getOrElse(dayOfWeekIdx) { false } }
            .map { r -> TodayRoutineItem(routine = RoutineResponse.from(r), completed = r.id in todayLoggedIds) }

        val recentCbt = cbtEntryRepository.findTopByUserIdOrderByCreatedAtDesc(userId)?.let { e ->
            CbtEntryResponse.from(e, cbtEntryTagRepository.findAllByEntryId(e.id!!).map { it.tag })
        }

        val greeting = when {
            now.hour < 12 -> "좋은 아침이에요!"
            now.hour < 18 -> "좋은 오후예요!"
            else -> "좋은 저녁이에요!"
        }

        return DashboardResponse(
            greeting = greeting,
            date = today,
            focusTask = focusTask,
            todayTasks = todayBlocks,
            todayRoutines = todayRoutines,
            recentCBT = recentCbt,
            routineProgress = RoutineProgress(todayRoutines.count { it.completed }, todayRoutines.size),
        )
    }
}
