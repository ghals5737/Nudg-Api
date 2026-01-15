package com.example.nudgapi.repository

import com.example.nudgapi.domain.goal.Goals
import org.springframework.data.jpa.repository.JpaRepository

interface GoalsRepository : JpaRepository<Goals, Long> {
    fun findByUserId(userId: Long): List<Goals>
}
