package com.example.nudgapi.domain.schedule

import com.example.nudgapi.domain.global.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "schedules")
class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false)
    var user_id: Long,
    @Column(nullable = false)
    var title: String,
    @Column(nullable = true, updatable = false)
    var start_time: LocalDateTime?=null,
    @Column(nullable = true, updatable = false)
    var end_time: LocalDateTime?=null,
    @Column(nullable = true)
    var color: String?=null,
    @Column(nullable = true)
    var block_type: String?=null,
    @Column(nullable = true)
    var is_completed: Boolean?=false,
): BaseEntity() {
}