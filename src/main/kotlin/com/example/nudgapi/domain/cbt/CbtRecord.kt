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
    @Column(nullable = false)
    var user_id: Long,
    @CreatedDate
    @Column(nullable = true, updatable = false)
    var recorded_at: LocalDateTime?=null,
    @Column(nullable = false)
    var mood_score: Int,
    @Column(nullable = true)
    var mood_label: String?=null,
    @Column(nullable = true)
    var emoji: String?=null,
    @Column(nullable = true)
    var impulse: String?=null,
    @Column(nullable = true)
    var coping_method: String?=null,
    @Column(nullable = true)
    var location: String?=null,
    @Column(nullable = true)
    var result_status: String?=null,
    @Column(nullable = true)
    var notes: String?=null,
): BaseEntity(){
}