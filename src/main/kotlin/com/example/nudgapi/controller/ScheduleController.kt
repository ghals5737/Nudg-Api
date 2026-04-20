package com.example.nudgapi.controller

import com.example.nudgapi.dto.*
import com.example.nudgapi.security.AuthPrincipal
import com.example.nudgapi.service.ScheduleService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/schedule")
class ScheduleController(private val scheduleService: ScheduleService) {

    @GetMapping
    fun list(@AuthenticationPrincipal p: AuthPrincipal, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate) =
        ApiResponse(scheduleService.list(p.userId, date))

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@AuthenticationPrincipal p: AuthPrincipal, @RequestBody req: CreateScheduleBlockRequest) =
        ApiResponse(scheduleService.create(p.userId, req))

    @GetMapping("/{blockId}")
    fun get(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable blockId: Long) =
        ApiResponse(scheduleService.get(p.userId, blockId))

    @PatchMapping("/{blockId}")
    fun update(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable blockId: Long, @RequestBody req: UpdateScheduleBlockRequest) =
        ApiResponse(scheduleService.update(p.userId, blockId, req))

    @DeleteMapping("/{blockId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable blockId: Long) =
        scheduleService.delete(p.userId, blockId)

    @PostMapping("/{blockId}/snooze")
    fun snooze(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable blockId: Long, @RequestBody req: SnoozeRequest) =
        ApiResponse(scheduleService.snooze(p.userId, blockId, req))
}
