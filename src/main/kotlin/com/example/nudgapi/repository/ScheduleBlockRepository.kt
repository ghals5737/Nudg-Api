package com.example.nudgapi.repository

import com.example.nudgapi.domain.ScheduleBlock
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface ScheduleBlockRepository : JpaRepository<ScheduleBlock, Long> {
    fun findAllByUserIdAndBlockDateOrderByStartTimeAsc(userId: Long, blockDate: LocalDate): List<ScheduleBlock>
}
