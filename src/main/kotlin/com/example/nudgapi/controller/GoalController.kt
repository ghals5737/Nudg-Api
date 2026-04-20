package com.example.nudgapi.controller

import com.example.nudgapi.dto.*
import com.example.nudgapi.security.AuthPrincipal
import com.example.nudgapi.service.GoalService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/goals")
class GoalController(private val goalService: GoalService) {

    @GetMapping
    fun list(@AuthenticationPrincipal p: AuthPrincipal) = ApiResponse(goalService.list(p.userId))

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@AuthenticationPrincipal p: AuthPrincipal, @RequestBody req: CreateGoalRequest) =
        ApiResponse(goalService.create(p.userId, req))

    @GetMapping("/{goalId}")
    fun get(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable goalId: Long) =
        ApiResponse(goalService.get(p.userId, goalId))

    @PatchMapping("/{goalId}")
    fun update(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable goalId: Long, @RequestBody req: UpdateGoalRequest) =
        ApiResponse(goalService.update(p.userId, goalId, req))

    @DeleteMapping("/{goalId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable goalId: Long) =
        goalService.delete(p.userId, goalId)

    @PatchMapping("/{goalId}/steps/{stepId}")
    fun updateStep(
        @AuthenticationPrincipal p: AuthPrincipal,
        @PathVariable goalId: Long,
        @PathVariable stepId: Long,
        @RequestBody req: UpdateGoalStepRequest,
    ) = ApiResponse(goalService.updateStep(p.userId, goalId, stepId, req))
}
