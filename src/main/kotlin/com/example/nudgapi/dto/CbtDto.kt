package com.example.nudgapi.dto

import com.example.nudgapi.domain.CbtEntry
import java.time.Instant

data class CbtEntryResponse(
    val id: Long,
    val userId: Long,
    val emoji: String,
    val mood: String,
    val moodBg: String,
    val moodText: String,
    val content: String,
    val tags: List<String>?,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    companion object {
        fun from(e: CbtEntry, tags: List<String>) = CbtEntryResponse(
            id = e.id!!,
            userId = e.userId,
            emoji = e.emoji,
            mood = e.mood,
            moodBg = e.moodBg,
            moodText = e.moodText,
            content = e.content,
            tags = tags.ifEmpty { null },
            createdAt = e.createdAt,
            updatedAt = e.updatedAt,
        )
    }
}

data class CreateCbtEntryRequest(
    val emoji: String,
    val mood: String,
    val moodBg: String? = null,
    val moodText: String? = null,
    val content: String,
    val tags: List<String>? = null,
)

data class UpdateCbtEntryRequest(
    val emoji: String? = null,
    val mood: String? = null,
    val moodBg: String? = null,
    val moodText: String? = null,
    val content: String? = null,
    val tags: List<String>? = null,
)
