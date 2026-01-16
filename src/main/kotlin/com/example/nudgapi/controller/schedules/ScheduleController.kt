package com.example.nudgapi.controller.schedules

import com.example.nudgapi.dto.user.CreateScheduleRequest
import com.example.nudgapi.dto.user.ScheduleResponse
import com.example.nudgapi.dto.user.ScheduleDetailResponse
import com.example.nudgapi.service.schedules.ScheduleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/schedules")
class ScheduleController(
    private val scheduleService: ScheduleService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSchedule(@RequestBody request: CreateScheduleRequest): ScheduleResponse {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return scheduleService.createSchedule(request, userId)
    }

    @GetMapping
    fun getSchedules(): List<ScheduleResponse> {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        // TODO: Implement filtering and pagination
        return scheduleService.getSchedules(userId)
    }

    @GetMapping("/{scheduleId}")
    fun getSchedule(@PathVariable scheduleId: Long): ScheduleDetailResponse {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return scheduleService.getSchedule(scheduleId, userId)
    }
}