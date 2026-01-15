package com.example.nudgapi.domain.goal

import com.example.nudgapi.domain.global.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "goals")
class Goals(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var userId: Long,

    @Column(nullable = true)
    var parentGoalId: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Column(nullable = true)
    var status: String? = "todo", // 'todo', 'in_progress', 'done', 'archived'

    @Column(nullable = true)
    var dueDate: LocalDate? = null,

    @Column(nullable = true)
    var color: String? = null,

): BaseEntity() {

}