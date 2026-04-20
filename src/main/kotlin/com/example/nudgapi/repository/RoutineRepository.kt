package com.example.nudgapi.repository

import com.example.nudgapi.domain.Routine
import org.springframework.data.jpa.repository.JpaRepository

interface RoutineRepository : JpaRepository<Routine, Long> {
    fun findAllByUserIdOrderByCreatedAtAsc(userId: Long): List<Routine>
}
