package com.example.nudgapi.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.time.LocalTime

@Entity
@Table(name = "routines")
@EntityListeners(AuditingEntityListener::class)
class Routine(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var icon: String = "check_circle",

    @Column(name = "icon_bg", nullable = false)
    var iconBg: String = "#cce8e4",

    @Column(nullable = false)
    var duration: Int,

    @Column(name = "scheduled_time", nullable = false)
    var scheduledTime: LocalTime,

    @Column(nullable = false, length = 63)
    var days: String,

    @Column(nullable = false)
    var active: Boolean = true,

    @Column(name = "smart_reminders", nullable = false)
    var smartReminders: Boolean = true,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),
) {
    fun getDaysList(): List<Boolean> = days.split(",").map { it.trim().toBoolean() }

    companion object {
        fun encodeDays(list: List<Boolean>): String = list.joinToString(",")
    }
}
