package com.example.nudgapi.domain.routine

import com.example.nudgapi.domain.global.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalTime

@Entity
@Table(name = "routines")
class Routines (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var userId: Long,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = true)
    var emoji: String? = null,

    @Column(nullable = true)
    var durationMinutes: Int? = null,

    @Column(name = "start_time")
    var startTime: LocalTime? = null,

    @Column(nullable = true)
    var recurrenceRule: String? = null, // e.g., "RRULE:FREQ=WEEKLY;BYDAY=MO,WE,FR"

    @Column(nullable = true)
    var notificationType: String? = null,

    @Column(nullable = true)
    var notificationMessage: String? = null,

    @Column(nullable = true)
    var isActive: Boolean? = null,

): BaseEntity()