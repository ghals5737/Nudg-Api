package com.example.nudgapi.repository

import com.example.nudgapi.domain.CbtEntry
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CbtEntryRepository : JpaRepository<CbtEntry, Long> {
    fun findAllByUserId(userId: Long, pageable: Pageable): Page<CbtEntry>
    fun findTopByUserIdOrderByCreatedAtDesc(userId: Long): CbtEntry?
}
