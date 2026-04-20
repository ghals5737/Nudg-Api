package com.example.nudgapi.service

import com.example.nudgapi.domain.Routine
import com.example.nudgapi.domain.RoutineLog
import com.example.nudgapi.dto.*
import com.example.nudgapi.exception.BadRequestException
import com.example.nudgapi.exception.ForbiddenException
import com.example.nudgapi.exception.NotFoundException
import com.example.nudgapi.repository.RoutineLogRepository
import com.example.nudgapi.repository.RoutineRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class RoutineService(
    private val routineRepository: RoutineRepository,
    private val routineLogRepository: RoutineLogRepository,
) {
    private val timeFmt = DateTimeFormatter.ofPattern("HH:mm")

    @Transactional(readOnly = true)
    fun list(userId: Long): List<RoutineWithRhythmResponse> {
        val today = LocalDate.now()
        return routineRepository.findAllByUserIdOrderByCreatedAtAsc(userId).map { r ->
            val (rhythm, rate) = computeRhythm(r.id!!, today.minusDays(6), today)
            RoutineWithRhythmResponse.from(r, rhythm, rate)
        }
    }

    @Transactional
    fun create(userId: Long, req: CreateRoutineRequest): RoutineResponse {
        require(req.days.size == 7) { "days must have exactly 7 elements" }
        return RoutineResponse.from(routineRepository.save(Routine(
            userId = userId,
            title = req.title,
            icon = req.icon ?: "check_circle",
            iconBg = req.iconBg ?: "#cce8e4",
            duration = req.duration,
            scheduledTime = LocalTime.parse(req.time, timeFmt),
            days = Routine.encodeDays(req.days),
            smartReminders = req.smartReminders,
        )))
    }

    @Transactional(readOnly = true)
    fun get(userId: Long, routineId: Long): RoutineWithRhythmResponse {
        val r = findOwned(userId, routineId)
        val today = LocalDate.now()
        val (rhythm, rate) = computeRhythm(routineId, today.minusDays(6), today)
        return RoutineWithRhythmResponse.from(r, rhythm, rate)
    }

    @Transactional
    fun update(userId: Long, routineId: Long, req: UpdateRoutineRequest): RoutineResponse {
        val r = findOwned(userId, routineId)
        req.title?.let { r.title = it }
        req.icon?.let { r.icon = it }
        req.iconBg?.let { r.iconBg = it }
        req.duration?.let { r.duration = it }
        req.time?.let { r.scheduledTime = LocalTime.parse(it, timeFmt) }
        req.days?.let { require(it.size == 7); r.days = Routine.encodeDays(it) }
        req.active?.let { r.active = it }
        req.smartReminders?.let { r.smartReminders = it }
        return RoutineResponse.from(routineRepository.save(r))
    }

    @Transactional
    fun delete(userId: Long, routineId: Long) = routineRepository.delete(findOwned(userId, routineId))

    @Transactional
    fun toggle(userId: Long, routineId: Long): RoutineResponse {
        val r = findOwned(userId, routineId)
        r.active = !r.active
        return RoutineResponse.from(routineRepository.save(r))
    }

    @Transactional
    fun log(userId: Long, routineId: Long, req: RoutineLogRequest) {
        findOwned(userId, routineId)
        val date = LocalDate.parse(req.date)
        if (routineLogRepository.existsByRoutineIdAndLogDate(routineId, date))
            throw BadRequestException("Already logged for this date")
        routineLogRepository.save(RoutineLog(routineId = routineId, userId = userId, logDate = date))
    }

    @Transactional(readOnly = true)
    fun rhythm(userId: Long, routineId: Long, days: Int): RoutineRhythmResponse {
        findOwned(userId, routineId)
        val today = LocalDate.now()
        val from = today.minusDays(days.toLong() - 1)
        val logs = routineLogRepository.findAllByRoutineIdAndLogDateBetween(routineId, from, today)
            .associateBy { it.logDate }
        val entries = (0 until days).map { offset ->
            val date = from.plusDays(offset.toLong())
            RhythmEntry(date.toString(), logs[date]?.completed ?: false)
        }
        val completed = entries.count { it.completed }
        return RoutineRhythmResponse(
            routineId = routineId,
            rhythm = entries,
            successRate = if (days > 0) (completed * 100) / days else 0,
        )
    }

    private fun findOwned(userId: Long, routineId: Long): Routine {
        val r = routineRepository.findById(routineId).orElseThrow { NotFoundException("Routine not found") }
        if (r.userId != userId) throw ForbiddenException("Access denied")
        return r
    }

    private fun computeRhythm(routineId: Long, from: LocalDate, to: LocalDate): Pair<List<Boolean>, Int> {
        val logs = routineLogRepository.findAllByRoutineIdAndLogDateBetween(routineId, from, to)
            .associateBy { it.logDate }
        var d = from
        val rhythm = mutableListOf<Boolean>()
        while (!d.isAfter(to)) { rhythm.add(logs[d]?.completed ?: false); d = d.plusDays(1) }
        val rate = if (rhythm.isNotEmpty()) (rhythm.count { it } * 100) / rhythm.size else 0
        return rhythm to rate
    }
}
