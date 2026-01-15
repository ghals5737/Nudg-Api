package com.example.nudgapi.repository

import com.example.nudgapi.domain.cbt.CbtRecord
import org.springframework.data.jpa.repository.JpaRepository

interface CbtRecordRepository : JpaRepository<CbtRecord, Long> {
    fun findByUser_id(userId: Long): List<CbtRecord>
}
