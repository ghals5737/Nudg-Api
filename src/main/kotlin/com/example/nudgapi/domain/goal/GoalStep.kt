package com.example.nudgapi.domain.goal

import com.example.nudgapi.domain.global.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "goal_steps")
class GoalStep (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false)
    var goal_id: Long,
    @Column(nullable = false)
    var title: String,
    @Column(nullable = true)
    var estimated_duration: Int?=null,
    @Column(nullable = true)
    var is_completed: Boolean?=false,
    @Column(nullable = true)
    var step_order: Int?=null,
): BaseEntity() {
}