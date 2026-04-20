package com.example.nudgapi.service

import com.example.nudgapi.domain.ScheduleBlock
import com.example.nudgapi.domain.TaskStatus
import com.example.nudgapi.domain.TaskTag
import com.example.nudgapi.dto.*
import com.example.nudgapi.exception.ForbiddenException
import com.example.nudgapi.exception.NotFoundException
import com.example.nudgapi.repository.ScheduleBlockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class ScheduleService(private val repo: ScheduleBlockRepository) {

    @Transactional(readOnly = true)
    fun list(userId: Long, date: LocalDate) =
        repo.findAllByUserIdAndBlockDateOrderByStartTimeAsc(userId, date).map { ScheduleBlockResponse.from(it) }

    @Transactional
    fun create(userId: Long, req: CreateScheduleBlockRequest) = ScheduleBlockResponse.from(repo.save(
        ScheduleBlock(
            userId = userId,
            title = req.title,
            blockDate = req.date,
            startTime = req.startTime,
            endTime = req.endTime,
            duration = req.duration,
            location = req.location,
            tag = req.tag?.let { runCatching { TaskTag.valueOf(it) }.getOrNull() },
        )
    ))

    @Transactional(readOnly = true)
    fun get(userId: Long, blockId: Long) = ScheduleBlockResponse.from(findOwned(userId, blockId))

    @Transactional
    fun update(userId: Long, blockId: Long, req: UpdateScheduleBlockRequest): ScheduleBlockResponse {
        val b = findOwned(userId, blockId)
        req.title?.let { b.title = it }
        req.date?.let { b.blockDate = it }
        req.startTime?.let { b.startTime = it }
        req.endTime?.let { b.endTime = it }
        req.duration?.let { b.duration = it }
        req.location?.let { b.location = it }
        req.tag?.let { b.tag = runCatching { TaskTag.valueOf(it) }.getOrNull() }
        req.status?.let { b.status = TaskStatus.valueOf(it) }
        return ScheduleBlockResponse.from(repo.save(b))
    }

    @Transactional
    fun delete(userId: Long, blockId: Long) = repo.delete(findOwned(userId, blockId))

    @Transactional
    fun snooze(userId: Long, blockId: Long, req: SnoozeRequest): ScheduleBlockResponse {
        val b = findOwned(userId, blockId)
        val mins = req.minutes / 60.0
        b.startTime += mins; b.endTime += mins; b.status = TaskStatus.snoozed
        return ScheduleBlockResponse.from(repo.save(b))
    }

    private fun findOwned(userId: Long, blockId: Long): ScheduleBlock {
        val b = repo.findById(blockId).orElseThrow { NotFoundException("Block not found") }
        if (b.userId != userId) throw ForbiddenException("Access denied")
        return b
    }
}
