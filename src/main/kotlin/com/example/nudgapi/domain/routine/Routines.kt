package com.example.nudgapi.domain.routine

import com.example.nudgapi.domain.global.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
@Table(name = "routines")
class Routines (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false)
    var user_id: Long,
    @Column(nullable = false)
    var title: String,
    @Column(nullable = true)
    var emoji: String?=null,
    @Column(nullable = true)
    var duration_minutes: Int?=null,
    @Column(name = "start_time")
    var startTime: LocalTime? = null,
    @Column(nullable = true)
    var repeat_days: String?=null,
    @Column(nullable = true)
    var notification_type: String?=null,
    @Column(nullable = true)
    var notification_message: String?=null,
    @Column(nullable = true)
    var is_active: Boolean?=null,
): BaseEntity(){
}