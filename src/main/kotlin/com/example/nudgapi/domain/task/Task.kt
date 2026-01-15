package com.example.nudgapi.domain.task

import com.example.nudgapi.domain.global.BaseEntity
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "tasks")
class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var userId: Long,

    @Column(nullable = true)
    var goalId: Long? = null,

    @Column(nullable = true)
    var routineId: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = true)
    var scheduledDate: LocalDate? = null,

    @Column(nullable = true)
    var completedAt: LocalDateTime? = null,

    @Column(nullable = false)
    var status: String = "pending", // 'pending', 'completed', 'skipped'

    @Column(nullable = true)
    var startTime: LocalDateTime? = null,

    @Column(nullable = true)
    var endTime: LocalDateTime? = null,

    @Column(nullable = true)
    var color: String? = null,

) : BaseEntity()
