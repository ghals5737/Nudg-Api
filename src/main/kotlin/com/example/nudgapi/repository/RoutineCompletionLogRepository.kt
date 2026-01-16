package com.example.nudgapi.repository

import com.example.nudgapi.domain.routine.RoutineCompletionLog
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface RoutineCompletionLogRepository : JpaRepository<RoutineCompletionLog, Long> {
    fun findByRoutineIdAndCompletionDate(routineId: Long, completionDate: LocalDate): RoutineCompletionLog?
}
