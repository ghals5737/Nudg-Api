package com.example.nudgapi.controller

import com.example.nudgapi.dto.*
import com.example.nudgapi.security.AuthPrincipal
import com.example.nudgapi.service.CbtService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cbt")
class CbtController(private val cbtService: CbtService) {

    @GetMapping
    fun list(@AuthenticationPrincipal p: AuthPrincipal, @RequestParam(defaultValue = "1") page: Int, @RequestParam(defaultValue = "20") limit: Int) =
        cbtService.list(p.userId, page, limit)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@AuthenticationPrincipal p: AuthPrincipal, @RequestBody req: CreateCbtEntryRequest) =
        ApiResponse(cbtService.create(p.userId, req))

    @GetMapping("/{entryId}")
    fun get(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable entryId: Long) =
        ApiResponse(cbtService.get(p.userId, entryId))

    @PatchMapping("/{entryId}")
    fun update(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable entryId: Long, @RequestBody req: UpdateCbtEntryRequest) =
        ApiResponse(cbtService.update(p.userId, entryId, req))

    @DeleteMapping("/{entryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@AuthenticationPrincipal p: AuthPrincipal, @PathVariable entryId: Long) =
        cbtService.delete(p.userId, entryId)
}
