package com.example.nudgapi.dto

import java.time.LocalDate

data class TodayRoutineItem(val routine: RoutineResponse, val completed: Boolean)
data class RoutineProgress(val completed: Int, val total: Int)
data class TodayRoutinesResponse(val items: List<TodayRoutineItem>, val progress: RoutineProgress)

data class DashboardResponse(
    val greeting: String,
    val date: LocalDate,
    val focusTask: ScheduleBlockResponse?,
    val todayTasks: List<ScheduleBlockResponse>,
    val todayRoutines: List<TodayRoutineItem>,
    val recentCBT: CbtEntryResponse?,
    val routineProgress: RoutineProgress,
)
