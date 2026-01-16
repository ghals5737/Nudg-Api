package com.example.nudgapi.dto.user

import com.example.nudgapi.domain.cbt.CbtRecord
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class CreateCbtEntryRequest(
    val moodScore: Int,
    val impulse: String,
    val copingMethod: String,
    val location: String,
    val notes: String
)

data class UpdateCbtEntryRequest(
    val moodScore: Int?,
    val impulse: String?,
    val copingMethod: String?,
    val location: String?,
    val result: String?,
    val notes: String?
)

import java.time.LocalDate
import java.time.LocalTime

data class CbtEntryResponse(
    val id: Long?,
    val date: LocalDate?,
    val time: LocalTime?,
    val emoji: String?,
    val mood: String?,
    val moodColor: String?,
    val impulse: String?,
    val copingMethod: String?,
    val result: String?,
    val createdAt: String?
) {
    // [중요] 이 companion object 부분이 있어야 .from()을 호출할 수 있습니다.
    companion object {
        fun from(record: CbtRecord): CbtEntryResponse {
            return CbtEntryResponse(
                id = record.id,
                date = record.recordedAt?.toLocalDate(),
                time = record.recordedAt?.toLocalTime(),
                emoji = record.emoji,
                mood = record.moodLabel,
                moodColor = "green", // 필요 시 로직 추가 (예: 점수에 따른 색상 반환)
                impulse = record.impulse,
                copingMethod = record.copingMethod,
                result = record.resultStatus,
                createdAt = record.createdDate?.toString()
            )
        }
    }
}

data class CbtEntryDetailResponse(
    val id: Long?,
    val date: String?,
    val time: String?,
    val emoji: String?,
    val mood: String?,
    val moodScore: Int,
    val moodColor: String?,
    val title: String?,
    val impulse: String?,
    val copingMethod: String?,
    val location: String?,
    val result: String?,
    val notes: String?,
    val createdAt: String?,
    val updatedAt: String?
)

data class CbtStatisticsResponse(
    val totalEntries: Int,
    val successRate: Double,
    val moodDistribution: MoodDistribution,
    val topCopingMethods: List<TopCopingMethod>
)

data class MoodDistribution(
    val green: Int,
    val orange: Int,
    val red: Int
)

data class TopCopingMethod(
    val method: String,
    val count: Int,
    val successRate: Double
)

{
    companion object {
        private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        fun from(record: CbtRecord): CbtEntryResponse {
            // This is a simplified mapping.
            // You'll need to implement the logic for mood, moodColor, etc.
            return CbtEntryResponse(
                id = record.id,
                date = record.recordedAt?.toLocalDate(),
                time = record.recordedAt?.toLocalTime(),
                emoji = record.emoji,
                mood = record.moodLabel,
                moodColor = "green", // placeholder
                impulse = record.impulse,
                copingMethod = record.copingMethod,
                result = record.resultStatus,
                createdAt = record.createdDate.toString()
            )
        }

        fun fromDetail(record: CbtRecord): CbtEntryDetailResponse {
            return CbtEntryDetailResponse(
                id = record.id,
                date = record.recordedAt?.format(dateFormatter),
                time = record.recordedAt?.format(timeFormatter),
                emoji = record.emoji,
                mood = record.moodLabel,
                moodScore = record.moodScore,
                moodColor = "green", // placeholder
                title = "Placeholder Title", // placeholder
                impulse = record.impulse,
                copingMethod = record.copingMethod,
                location = record.location,
                result = record.resultStatus,
                notes = record.notes,
                createdAt = record.createdDate.toString(),
                updatedAt = record.updatedDate.toString()
            )
        }
    }
}

// [추가] UserService에서 사용하는 DTO 정의
data class CbtRecordDto(
    val id: Long?,
    val userId: Long,
    val recordedAt: java.time.LocalDateTime?,
    val moodScore: Int,
    val moodLabel: String?,
    val emoji: String?,
    val impulse: String?,
    val copingMethod: String?,
    val location: String?,
    val resultStatus: String?,
    val notes: String?
)
