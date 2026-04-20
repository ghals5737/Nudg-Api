package com.example.nudgapi.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.time.LocalDate

@Entity
@Table(name = "schedule_blocks")
@EntityListeners(AuditingEntityListener::class)
class ScheduleBlock(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(nullable = false)
    var title: String,

    @Column(name = "block_date", nullable = false)
    var blockDate: LocalDate,

    @Column(name = "start_time", nullable = false)
    var startTime: Double,

    @Column(name = "end_time", nullable = false)
    var endTime: Double,

    @Column(nullable = false)
    var duration: Double,

    @Column
    var location: String? = null,

    @Enumerated(EnumType.STRING)
    @Column
    var tag: TaskTag? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TaskStatus = TaskStatus.pending,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),
)
