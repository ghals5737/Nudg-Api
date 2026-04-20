package com.example.nudgapi.repository

import com.example.nudgapi.domain.Goal
import org.springframework.data.jpa.repository.JpaRepository

interface GoalRepository : JpaRepository<Goal, Long> {
    fun findAllByUserIdOrderByCreatedAtAsc(userId: Long): List<Goal>
}
