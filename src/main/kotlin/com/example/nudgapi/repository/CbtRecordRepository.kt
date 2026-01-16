package com.example.nudgapi.repository

import com.example.nudgapi.domain.cbt.CbtRecord
import org.springframework.data.jpa.repository.JpaRepository

interface CbtRecordRepository : JpaRepository<CbtRecord, Long> {
    // [수정] findByUser_id -> findByUserId
    fun findByUserId(userId: Long): List<CbtRecord>
}
