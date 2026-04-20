package com.example.nudgapi.service

import com.example.nudgapi.domain.Goal
import com.example.nudgapi.domain.GoalStep
import com.example.nudgapi.dto.*
import com.example.nudgapi.exception.ForbiddenException
import com.example.nudgapi.exception.NotFoundException
import com.example.nudgapi.repository.GoalRepository
import com.example.nudgapi.repository.GoalStepRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GoalService(
    private val goalRepository: GoalRepository,
    private val goalStepRepository: GoalStepRepository,
) {

    @Transactional(readOnly = true)
    fun list(userId: Long): List<GoalResponse> =
        goalRepository.findAllByUserIdOrderByCreatedAtAsc(userId).map { g ->
            GoalResponse.from(g, goalStepRepository.findAllByGoalIdOrderByOrderIdxAsc(g.id!!))
        }

    @Transactional
    fun create(userId: Long, req: CreateGoalRequest): GoalResponse {
        val goal = goalRepository.save(
            Goal(
                userId = userId,
                title = req.title,
                subtitle = req.subtitle,
                icon = req.icon ?: "star",
                iconBg = req.iconBg ?: "#cce8e4",
                iconColor = req.iconColor ?: "#3d5653",
                progressColor = req.progressColor ?: "#006b64",
            )
        )
        val steps = req.steps.mapIndexed { idx, label ->
            goalStepRepository.save(GoalStep(goalId = goal.id!!, label = label, orderIdx = idx))
        }
        return GoalResponse.from(goal, steps)
    }

    @Transactional(readOnly = true)
    fun get(userId: Long, goalId: Long): GoalResponse {
        val goal = findOwned(userId, goalId)
        return GoalResponse.from(goal, goalStepRepository.findAllByGoalIdOrderByOrderIdxAsc(goalId))
    }

    @Transactional
    fun update(userId: Long, goalId: Long, req: UpdateGoalRequest): GoalResponse {
        val goal = findOwned(userId, goalId)
        req.title?.let { goal.title = it }
        req.subtitle?.let { goal.subtitle = it }
        req.icon?.let { goal.icon = it }
        req.iconBg?.let { goal.iconBg = it }
        req.iconColor?.let { goal.iconColor = it }
        req.progressColor?.let { goal.progressColor = it }
        req.progress?.let { goal.progress = it }
        val saved = goalRepository.save(goal)
        return GoalResponse.from(saved, goalStepRepository.findAllByGoalIdOrderByOrderIdxAsc(goalId))
    }

    @Transactional
    fun delete(userId: Long, goalId: Long) {
        val goal = findOwned(userId, goalId)
        goalStepRepository.deleteAllByGoalId(goalId)
        goalRepository.delete(goal)
    }

    @Transactional
    fun updateStep(userId: Long, goalId: Long, stepId: Long, req: UpdateGoalStepRequest): GoalResponse {
        findOwned(userId, goalId)
        val step = goalStepRepository.findById(stepId).orElseThrow { NotFoundException("Step not found") }
        if (step.goalId != goalId) throw ForbiddenException("Access denied")
        step.done = req.done
        goalStepRepository.save(step)
        val steps = goalStepRepository.findAllByGoalIdOrderByOrderIdxAsc(goalId)
        val goal = goalRepository.findById(goalId).get()
        if (steps.isNotEmpty()) {
            goal.progress = (steps.count { it.done } * 100) / steps.size
            goalRepository.save(goal)
        }
        return GoalResponse.from(goal, steps)
    }

    private fun findOwned(userId: Long, goalId: Long): Goal {
        val goal = goalRepository.findById(goalId).orElseThrow { NotFoundException("Goal not found") }
        if (goal.userId != userId) throw ForbiddenException("Access denied")
        return goal
    }
}
