package com.example.nudgapi.dto.user

import com.example.nudgapi.domain.task.Task
import java.time.LocalDateTime

data class CreateStepRequest(
    val title: String,
    val order: Int
)

data class UpdateStepRequest(
    val title: String?,
    val completed: Boolean?,
    val order: Int?
)

data class StepResponse(
    val id: Long?,
    val goalId: Long?,
    val title: String,
    val completed: Boolean,
    val order: Int,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(task: Task): StepResponse {
            return StepResponse(
                id = task.id,
                goalId = task.goalId,
                title = task.title,
                completed = task.completed,
                order = task.displayOrder,
                createdAt = task.createdDate,
                updatedAt = task.updatedDate
            )
        }
    }
}
