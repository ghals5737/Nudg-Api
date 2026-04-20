package com.example.nudgapi.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table(name = "goal_steps")
@EntityListeners(AuditingEntityListener::class)
class GoalStep(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "goal_id", nullable = false)
    val goalId: Long,

    @Column(nullable = false)
    var label: String,

    @Column(nullable = false)
    var done: Boolean = false,

    @Column(name = "order_idx", nullable = false)
    var orderIdx: Int = 0,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),
)
