package com.example.nudgapi.exception

import com.example.nudgapi.dto.ApiError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

class NotFoundException(msg: String) : RuntimeException(msg)
class ConflictException(msg: String) : RuntimeException(msg)
class ForbiddenException(msg: String) : RuntimeException(msg)
class BadRequestException(msg: String) : RuntimeException(msg)

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(e: NotFoundException) =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError(e.message ?: "Not found", 404))

    @ExceptionHandler(ConflictException::class)
    fun handleConflict(e: ConflictException) =
        ResponseEntity.status(HttpStatus.CONFLICT).body(ApiError(e.message ?: "Conflict", 409))

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbidden(e: ForbiddenException) =
        ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiError(e.message ?: "Forbidden", 403))

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(e: BadRequestException) =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError(e.message ?: "Bad request", 400))

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArg(e: IllegalArgumentException) =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError(e.message ?: "Invalid input", 400))
}
