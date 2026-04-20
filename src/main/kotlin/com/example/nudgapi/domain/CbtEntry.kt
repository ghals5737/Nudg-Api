package com.example.nudgapi.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table(name = "cbt_entries")
@EntityListeners(AuditingEntityListener::class)
class CbtEntry(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(nullable = false)
    var emoji: String,

    @Column(nullable = false)
    var mood: String,

    @Column(name = "mood_bg", nullable = false)
    var moodBg: String = "#cce8e4",

    @Column(name = "mood_text", nullable = false)
    var moodText: String = "#3d5653",

    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),
)
