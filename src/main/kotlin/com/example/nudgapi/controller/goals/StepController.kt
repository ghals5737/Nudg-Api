package com.example.nudgapi.controller.goals

import com.example.nudgapi.dto.user.CreateStepRequest
import com.example.nudgapi.dto.user.StepResponse
import com.example.nudgapi.dto.user.UpdateStepRequest
import com.example.nudgapi.service.goals.StepService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/goals/{goalId}/steps")
class StepController(
    private val stepService: StepService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createStep(
        @PathVariable goalId: Long,
        @RequestBody request: CreateStepRequest
    ): StepResponse {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return stepService.createStep(goalId, request, userId)
    }

    @GetMapping
    fun getSteps(@PathVariable goalId: Long): List<StepResponse> {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return stepService.getSteps(goalId, userId)
    }

    @PatchMapping("/{stepId}")
    fun updateStep(
        @PathVariable goalId: Long,
        @PathVariable stepId: Long,
        @RequestBody request: UpdateStepRequest
    ): StepResponse {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return stepService.updateStep(stepId, request, userId)
    }

    @DeleteMapping("/{stepId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteStep(
        @PathVariable goalId: Long,
        @PathVariable stepId: Long
    ) {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        stepService.deleteStep(stepId, userId)
    }

    @PostMapping("/{stepId}/complete")
    fun completeStep(
        @PathVariable goalId: Long,
        @PathVariable stepId: Long
    ): StepResponse {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return stepService.updateStepCompletion(stepId, true, userId)
    }

    @PostMapping("/{stepId}/uncomplete")
    fun uncompleteStep(
        @PathVariable goalId: Long,
        @PathVariable stepId: Long
    ): StepResponse {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return stepService.updateStepCompletion(stepId, false, userId)
    }
}