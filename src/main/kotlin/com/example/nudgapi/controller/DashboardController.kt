package com.example.nudgapi.controller

import com.example.nudgapi.dto.ApiResponse
import com.example.nudgapi.security.AuthPrincipal
import com.example.nudgapi.service.DashboardService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/dashboard")
class DashboardController(private val dashboardService: DashboardService) {

    @GetMapping
    fun getDashboard(@AuthenticationPrincipal p: AuthPrincipal) =
        ApiResponse(dashboardService.getDashboard(p.userId))
}
