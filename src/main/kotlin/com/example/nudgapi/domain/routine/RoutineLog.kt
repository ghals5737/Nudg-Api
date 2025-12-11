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

@Entity
@Table(name = "routine_logs")
class RoutineLog (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false)
    var routine_id: Long,
    @Column(nullable = true, updatable = false)
    var recorded_at: LocalDateTime?=null,
    @Column(nullable = true)
    var is_success: Boolean?=null,
): BaseEntity() {
}