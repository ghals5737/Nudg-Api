package com.example.nudgapi.repository

import com.example.nudgapi.domain.RoutineLog
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface RoutineLogRepository : JpaRepository<RoutineLog, Long> {
    fun findAllByRoutineIdAndLogDateBetween(routineId: Long, from: LocalDate, to: LocalDate): List<RoutineLog>
    fun existsByRoutineIdAndLogDate(routineId: Long, logDate: LocalDate): Boolean
    fun findAllByUserIdAndLogDate(userId: Long, logDate: LocalDate): List<RoutineLog>
    fun deleteByRoutineIdAndLogDate(routineId: Long, logDate: LocalDate): Long
}
