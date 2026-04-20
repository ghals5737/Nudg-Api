package com.example.nudgapi.repository

import com.example.nudgapi.domain.CbtEntryTag
import org.springframework.data.jpa.repository.JpaRepository

interface CbtEntryTagRepository : JpaRepository<CbtEntryTag, Long> {
    fun findAllByEntryId(entryId: Long): List<CbtEntryTag>
    fun deleteAllByEntryId(entryId: Long)
}
