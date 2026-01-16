package com.example.nudgapi.controller.routines

import com.example.nudgapi.dto.user.*
import com.example.nudgapi.service.routines.RoutineService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/routines")
class RoutineController(
    private val routineService: RoutineService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createRoutine(@RequestBody request: CreateRoutineRequest): RoutineResponse {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return routineService.createRoutine(request, userId)
    }

    @GetMapping
    fun getRoutines(): List<RoutineResponse> {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        // TODO: Implement filtering and pagination
        return routineService.getRoutines(userId)
    }

    @GetMapping("/{routineId}")
    fun getRoutine(@PathVariable routineId: Long): RoutineDetailResponse {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return routineService.getRoutine(routineId, userId)
    }

    @PatchMapping("/{routineId}")
    fun updateRoutine(
        @PathVariable routineId: Long,
        @RequestBody request: UpdateRoutineRequest
    ): RoutineDetailResponse {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return routineService.updateRoutine(routineId, request, userId)
    }

    @DeleteMapping("/{routineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteRoutine(@PathVariable routineId: Long) {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        routineService.deleteRoutine(routineId, userId)
    }

    @PatchMapping("/{routineId}/status")
    fun updateRoutineStatus(
        @PathVariable routineId: Long,
        @RequestBody request: UpdateRoutineStatusRequest
    ): RoutineDetailResponse {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return routineService.updateRoutineStatus(routineId, request.active, userId)
    }

    @PostMapping("/{routineId}/complete")
    fun completeRoutine(
        @PathVariable routineId: Long,
        @RequestBody request: CompleteRoutineRequest
    ): CompleteRoutineResponse {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return routineService.completeRoutine(routineId, request.date, userId)
    }

    @PostMapping("/{routineId}/snooze")
    fun snoozeRoutine(
        @PathVariable routineId: Long,
        @RequestBody request: SnoozeRoutineRequest
    ): SuccessResponse {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return routineService.snoozeRoutine(routineId, request.minutes, userId)
    }
}