package com.example.nudgapi.service.cbtrecords

import com.example.nudgapi.domain.cbt.CbtRecord
import com.example.nudgapi.dto.user.*
import com.example.nudgapi.repository.CbtRecordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CbtService(
    private val cbtRecordRepository: CbtRecordRepository
) {

    @Transactional
    fun createCbtEntry(request: CreateCbtEntryRequest, userId: Long): CbtEntryResponse {
        val record = CbtRecord(
            userId = userId,
            moodScore = request.moodScore,
            impulse = request.impulse,
            copingMethod = request.copingMethod,
            location = request.location,
            notes = request.notes,
            recordedAt = LocalDateTime.now()
            // moodLabel, emoji, resultStatus will be set based on moodScore or other logic
        )
        
        // Add logic to determine moodLabel, emoji, and resultStatus
        record.moodLabel = "Good" // Placeholder
        record.emoji = "😀" // Placeholder
        record.resultStatus = "success" // Placeholder

        val savedRecord = cbtRecordRepository.save(record)
        return CbtEntryResponse.from(savedRecord)
    }

    @Transactional(readOnly = true)
    fun getCbtEntries(userId: Long): List<CbtEntryResponse> {
        val records = cbtRecordRepository.findByUserId(userId)
        return records.map { CbtEntryResponse.from(it) }
    }

    @Transactional(readOnly = true)
    fun getCbtEntry(cbtId: Long, userId: Long): CbtEntryDetailResponse {
        val record = cbtRecordRepository.findById(cbtId).orElseThrow {
            IllegalArgumentException("CBT record not found")
        }

        if (record.userId != userId) {
            throw IllegalAccessException("User not authorized to view this record")
        }

        return CbtEntryDetailResponse.fromDetail(record)
    }

    @Transactional
    fun updateCbtEntry(cbtId: Long, request: UpdateCbtEntryRequest, userId: Long): CbtEntryDetailResponse {
        val record = cbtRecordRepository.findById(cbtId).orElseThrow {
            IllegalArgumentException("CBT record not found")
        }

        if (record.userId != userId) {
            throw IllegalAccessException("User not authorized to update this record")
        }

        request.moodScore?.let { record.moodScore = it }
        request.impulse?.let { record.impulse = it }
        request.copingMethod?.let { record.copingMethod = it }
        request.location?.let { record.location = it }
        request.result?.let { record.resultStatus = it }
        request.notes?.let { record.notes = it }

        val updatedRecord = cbtRecordRepository.save(record)
        return CbtEntryDetailResponse.fromDetail(updatedRecord)
    }

    @Transactional
    fun deleteCbtEntry(cbtId: Long, userId: Long) {
        val record = cbtRecordRepository.findById(cbtId).orElseThrow {
            IllegalArgumentException("CBT record not found")
        }

        if (record.userId != userId) {
            throw IllegalAccessException("User not authorized to delete this record")
        }

        cbtRecordRepository.delete(record)
    }

    @Transactional(readOnly = true)
    fun getCbtStatistics(userId: Long, period: String): CbtStatisticsResponse {
        // TODO: Implement actual statistics calculation
        return CbtStatisticsResponse(
            totalEntries = 10,
            successRate = 0.8,
            moodDistribution = MoodDistribution(green = 5, orange = 3, red = 2),
            topCopingMethods = listOf(
                TopCopingMethod(method = "breathing", count = 5, successRate = 0.9),
                TopCopingMethod(method = "walk", count = 3, successRate = 0.7)
            )
        )
    }
}
