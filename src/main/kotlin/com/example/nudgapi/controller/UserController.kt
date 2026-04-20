package com.example.nudgapi.controller

import com.example.nudgapi.dto.*
import com.example.nudgapi.security.AuthPrincipal
import com.example.nudgapi.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getProfile(@AuthenticationPrincipal p: AuthPrincipal) = ApiResponse(userService.getProfile(p.userId))

    @PatchMapping
    fun updateProfile(@AuthenticationPrincipal p: AuthPrincipal, @RequestBody req: UpdateProfileRequest) =
        ApiResponse(userService.updateProfile(p.userId, req))

    @PatchMapping("/settings")
    fun updateSettings(@AuthenticationPrincipal p: AuthPrincipal, @RequestBody req: UpdateSettingsRequest) =
        ApiResponse(userService.updateSettings(p.userId, req))
}
