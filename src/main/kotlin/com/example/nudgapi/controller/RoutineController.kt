package com.example.nudgapi.controller

import com.example.nudgapi.dto.*
import com.example.nudgapi.security.AuthPrincipal
import com.example.nudgapi.service.RoutineService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/routines")
class RoutineController(private val routineService: RoutineService) {

    @GetMapping
    fun list(@AuthenticationPrincipal p: AuthPrincipal) = ApiResponse(routineService.list(p.userId))

    @GetMapping("/today")
    fun today(@AuthenticationPrincipal p: AuthPrincipal) = ApiResponse(routineService.today(p.userId))

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@AuthenticationPrincipal p: AuthPrincipal, @RequestBody req: CreateRoutineRequest) =
        ApiResponse(routineService.create(p.userId, req))

    @GetMapping("/{routineId}")
    fun get(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable routineId: Long) =
        ApiResponse(routineService.get(p.userId, routineId))

    @PatchMapping("/{routineId}")
    fun update(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable routineId: Long, @RequestBody req: UpdateRoutineRequest) =
        ApiResponse(routineService.update(p.userId, routineId, req))

    @DeleteMapping("/{routineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable routineId: Long) =
        routineService.delete(p.userId, routineId)

    @PatchMapping("/{routineId}/toggle")
    fun toggle(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable routineId: Long) =
        ApiResponse(routineService.toggle(p.userId, routineId))

    @PostMapping("/{routineId}/log")
    fun log(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable routineId: Long, @RequestBody req: RoutineLogRequest) =
        routineService.log(p.userId, routineId, req)

    @DeleteMapping("/{routineId}/log")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun unlog(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable routineId: Long, @RequestParam date: String) =
        routineService.unlog(p.userId, routineId, date)

    @GetMapping("/{routineId}/rhythm")
    fun rhythm(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable routineId: Long, @RequestParam(defaultValue = "7") days: Int) =
        ApiResponse(routineService.rhythm(p.userId, routineId, days))
}
