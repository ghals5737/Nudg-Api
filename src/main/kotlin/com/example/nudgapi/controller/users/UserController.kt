package com.example.nudgapi.controller.users

import com.example.nudgapi.dto.user.UserResponse
import com.example.nudgapi.service.users.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userService: UserService
) {

    @Operation(summary = "유저 정보 조회", description = "유저 ID로 유저의 상세 정보를 조회합니다.")
    @GetMapping("/{userId}")
    fun getUser(
        @Parameter(description = "조회할 유저의 ID") @PathVariable userId: Long
    ): UserResponse {
        return userService.getUser(userId)
    }
}