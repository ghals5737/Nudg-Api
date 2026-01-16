package com.example.nudgapi.repository

import com.example.nudgapi.domain.schedule.Schedule
import org.springframework.data.jpa.repository.JpaRepository

interface ScheduleRepository : JpaRepository<Schedule, Long> {
    fun findByUserId(userId: Long): List<Schedule>
}
