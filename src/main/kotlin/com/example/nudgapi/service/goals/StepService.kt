package com.example.nudgapi.service.goals

import com.example.nudgapi.dto.user.CreateStepRequest
import com.example.nudgapi.dto.user.StepResponse
import com.example.nudgapi.dto.user.UpdateStepRequest
import com.example.nudgapi.domain.task.Task
import com.example.nudgapi.repository.TaskRepository
import com.example.nudgapi.repository.GoalsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StepService(
    private val taskRepository: TaskRepository,
    private val goalsRepository: GoalsRepository
) {

    @Transactional
    fun createStep(goalId: Long, request: CreateStepRequest, userId: Long): StepResponse {
        val goal = goalsRepository.findById(goalId).orElseThrow {
            IllegalArgumentException("Goal not found")
        }

        if (goal.userId != userId) {
            throw IllegalAccessException("User not authorized to add step to this goal")
        }

        val task = Task(
            userId = userId,
            goalId = goalId,
            title = request.title,
            displayOrder = request.order,
            completed = false
        )

        val savedTask = taskRepository.save(task)
        return StepResponse.from(savedTask)
    }

    @Transactional(readOnly = true)
    fun getSteps(goalId: Long, userId: Long): List<StepResponse> {
        val goal = goalsRepository.findById(goalId).orElseThrow {
            IllegalArgumentException("Goal not found")
        }

        if (goal.userId != userId) {
            throw IllegalAccessException("User not authorized to view this goal")
        }

        val tasks = taskRepository.findByGoalIdOrderByDisplayOrderAsc(goalId)
        return tasks.map { StepResponse.from(it) }
    }

    @Transactional
    fun updateStep(stepId: Long, request: UpdateStepRequest, userId: Long): StepResponse {
        val task = taskRepository.findById(stepId).orElseThrow {
            IllegalArgumentException("Step not found")
        }

        if (task.userId != userId) {
            throw IllegalAccessException("User not authorized to update this step")
        }

        request.title?.let { task.title = it }
        request.completed?.let { task.completed = it }
        request.order?.let { task.displayOrder = it }

        val updatedTask = taskRepository.save(task)
        return StepResponse.from(updatedTask)
    }

    @Transactional
    fun deleteStep(stepId: Long, userId: Long) {
        val task = taskRepository.findById(stepId).orElseThrow {
            IllegalArgumentException("Step not found")
        }

        if (task.userId != userId) {
            throw IllegalAccessException("User not authorized to delete this step")
        }

        taskRepository.delete(task)
    }

    @Transactional
    fun updateStepCompletion(stepId: Long, completed: Boolean, userId: Long): StepResponse {
        val task = taskRepository.findById(stepId).orElseThrow {
            IllegalArgumentException("Step not found")
        }

        if (task.userId != userId) {
            throw IllegalAccessException("User not authorized to update this step")
        }

        task.completed = completed
        val updatedTask = taskRepository.save(task)
        return StepResponse.from(updatedTask)
    }
}
