package com.example.nudgapi.domain.goal

import com.example.nudgapi.domain.global.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "goal_tags")
class GoalTag (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false)
    var goal_id: Long,
    @Column(nullable = false)
    var tag_name: String,
): BaseEntity() {
}