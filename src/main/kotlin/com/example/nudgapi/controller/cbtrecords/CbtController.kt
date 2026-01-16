package com.example.nudgapi.controller.cbtrecords

import com.example.nudgapi.dto.user.*
import com.example.nudgapi.service.cbtrecords.CbtService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/cbt")
class CbtController(
    private val cbtService: CbtService
) {

    @PostMapping("/entries")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCbtEntry(@RequestBody request: CreateCbtEntryRequest): CbtEntryResponse {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return cbtService.createCbtEntry(request, userId)
    }

    @GetMapping("/entries")
    fun getCbtEntries(): List<CbtEntryResponse> {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        // TODO: Implement filtering and pagination based on query parameters
        return cbtService.getCbtEntries(userId)
    }

    @GetMapping("/entries/{cbtId}")
    fun getCbtEntry(@PathVariable cbtId: Long): CbtEntryDetailResponse {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return cbtService.getCbtEntry(cbtId, userId)
    }

    @PatchMapping("/entries/{cbtId}")
    fun updateCbtEntry(
        @PathVariable cbtId: Long,
        @RequestBody request: UpdateCbtEntryRequest
    ): CbtEntryDetailResponse {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return cbtService.updateCbtEntry(cbtId, request, userId)
    }

    @DeleteMapping("/entries/{cbtId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCbtEntry(@PathVariable cbtId: Long) {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        cbtService.deleteCbtEntry(cbtId, userId)
    }

    @GetMapping("/statistics")
    fun getCbtStatistics(@RequestParam(defaultValue = "week") period: String): CbtStatisticsResponse {
        // TODO: Get userId from authenticated principal
        val userId = 1L
        return cbtService.getCbtStatistics(userId, period)
    }
}