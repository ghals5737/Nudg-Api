package com.example.nudgapi.controller

import com.example.nudgapi.dto.ApiResponse
import com.example.nudgapi.dto.LoginRequest
import com.example.nudgapi.dto.SignupRequest
import com.example.nudgapi.security.AuthPrincipal
import com.example.nudgapi.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(@RequestBody req: SignupRequest) = authService.signup(req)

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest) = authService.login(req)

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun logout(@AuthenticationPrincipal p: AuthPrincipal) = authService.logout(p.userId)

    @GetMapping("/me")
    fun me(@AuthenticationPrincipal p: AuthPrincipal) = ApiResponse(authService.me(p.userId))
}
