package com.example.nudgapi.repository

import com.example.nudgapi.domain.GoalStep
import org.springframework.data.jpa.repository.JpaRepository

interface GoalStepRepository : JpaRepository<GoalStep, Long> {
    fun findAllByGoalIdOrderByOrderIdxAsc(goalId: Long): List<GoalStep>
    fun deleteAllByGoalId(goalId: Long)
}
