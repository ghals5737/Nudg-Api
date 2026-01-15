package com.example.nudgapi.repository

import com.example.nudgapi.domain.routine.Routines
import org.springframework.data.jpa.repository.JpaRepository

interface RoutinesRepository : JpaRepository<Routines, Long> {
    fun findByUserId(userId: Long): List<Routines>
}
