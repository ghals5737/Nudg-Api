package com.example.nudgapi.repository

import com.example.nudgapi.domain.task.Task
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository : JpaRepository<Task, Long> {
    fun findByUserId(userId: Long): List<Task>
    fun findByRoutineId(routineId: Long): List<Task>
}
