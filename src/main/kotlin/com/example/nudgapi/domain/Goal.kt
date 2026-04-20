package com.example.nudgapi.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table(name = "goals")
@EntityListeners(AuditingEntityListener::class)
class Goal(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(nullable = false)
    var title: String,

    @Column
    var subtitle: String? = null,

    @Column(nullable = false)
    var icon: String = "star",

    @Column(name = "icon_bg", nullable = false)
    var iconBg: String = "#cce8e4",

    @Column(name = "icon_color", nullable = false)
    var iconColor: String = "#3d5653",

    @Column(name = "progress_color", nullable = false)
    var progressColor: String = "#006b64",

    @Column(nullable = false)
    var progress: Int = 0,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),
)
