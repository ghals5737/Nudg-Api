package com.example.nudgapi.domain.schedule

import com.example.nudgapi.domain.global.BaseEntity
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
@Table(name = "schedules")
class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var userId: Long,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var date: LocalDate,

    @Column(nullable = false)
    var startTime: LocalTime,

    @Column(nullable = false)
    var endTime: LocalTime,

    @Column(nullable = true)
    var color: String? = null,

    @Column(nullable = true)
    var template: String? = null,

    @Column(columnDefinition = "TEXT")
    var notes: String? = null

) : BaseEntity()
