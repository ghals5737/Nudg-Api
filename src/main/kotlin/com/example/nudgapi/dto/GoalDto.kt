package com.example.nudgapi.dto

import com.example.nudgapi.domain.Goal
import com.example.nudgapi.domain.GoalStep
import java.time.Instant

data class GoalStepResponse(
    val id: Long,
    val label: String,
    val done: Boolean,
    val order: Int,
) {
    companion object {
        fun from(s: GoalStep) = GoalStepResponse(id = s.id!!, label = s.label, done = s.done, order = s.orderIdx)
    }
}

data class GoalResponse(
    val id: Long,
    val userId: Long,
    val title: String,
    val subtitle: String?,
    val icon: String,
    val iconBg: String,
    val iconColor: String,
    val progressColor: String,
    val progress: Int,
    val steps: List<GoalStepResponse>,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    companion object {
        fun from(g: Goal, steps: List<GoalStep>) = GoalResponse(
            id = g.id!!,
            userId = g.userId,
            title = g.title,
            subtitle = g.subtitle,
            icon = g.icon,
            iconBg = g.iconBg,
            iconColor = g.iconColor,
            progressColor = g.progressColor,
            progress = g.progress,
            steps = steps.map { GoalStepResponse.from(it) },
            createdAt = g.createdAt,
            updatedAt = g.updatedAt,
        )
    }
}

data class CreateGoalRequest(
    val title: String,
    val subtitle: String? = null,
    val icon: String? = null,
    val iconBg: String? = null,
    val iconColor: String? = null,
    val progressColor: String? = null,
    val steps: List<String> = emptyList(),
)

data class UpdateGoalRequest(
    val title: String? = null,
    val subtitle: String? = null,
    val icon: String? = null,
    val iconBg: String? = null,
    val iconColor: String? = null,
    val progressColor: String? = null,
    val progress: Int? = null,
    val steps: List<String>? = null,
)

data class UpdateGoalStepRequest(val done: Boolean)
