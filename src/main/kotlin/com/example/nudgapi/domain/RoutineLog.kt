package com.example.nudgapi.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.time.LocalDate

@Entity
@Table(name = "routine_logs")
@EntityListeners(AuditingEntityListener::class)
class RoutineLog(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "routine_id", nullable = false)
    val routineId: Long,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "log_date", nullable = false)
    val logDate: LocalDate,

    @Column(nullable = false)
    val completed: Boolean = true,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now(),
)
