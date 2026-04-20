package com.example.nudgapi.dto

data class ApiResponse<T>(val data: T)

data class PagedResponse<T>(
    val data: List<T>,
    val total: Long,
    val page: Int,
    val limit: Int,
)

data class ApiError(val error: String, val statusCode: Int)
