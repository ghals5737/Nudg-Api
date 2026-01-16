package com.example.nudgapi.domain.cbt

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
@Table(name = "cbt_records")
class CbtRecord (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "user_id", nullable = false)
    var userId: Long,

    @CreatedDate
    @Column(name = "recorded_at", nullable = true, updatable = false)
    var recordedAt: LocalDateTime? = null,

    @Column(name = "mood_score", nullable = false)
    var moodScore: Int,

    @Column(name = "mood_label", nullable = true)
    var moodLabel: String? = null,

    @Column(nullable = true)
    var emoji: String? = null,

    @Column(nullable = true)
    var impulse: String? = null,

    @Column(name = "coping_method", nullable = true)
    var copingMethod: String? = null,

    @Column(nullable = true)
    var location: String? = null,

    @Column(name = "result_status", nullable = true)
    var resultStatus: String? = null,
    
    @Column(nullable = true)
    var notes: String? = null,
): BaseEntity(){
}