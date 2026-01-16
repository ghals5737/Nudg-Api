package com.example.nudgapi.repository

import com.example.nudgapi.domain.goal.GoalTag
import org.springframework.data.jpa.repository.JpaRepository

interface GoalTagRepository : JpaRepository<GoalTag, Long> {
    fun findByGoalId(goalId: Long): List<GoalTag>
}
