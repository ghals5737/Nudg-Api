package com.example.nudgapi.domain.task

import com.example.nudgapi.domain.global.BaseEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "tasks")
class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var userId: Long,

    @Column(nullable = true)
    var goalId: Long? = null,

    @Column(nullable = true)
    var routineId: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = true)
    var scheduledDate: LocalDate? = null,

    @Column(nullable = false)
    var completed: Boolean = false,

    @Column(nullable = false)
    var displayOrder: Int = 0,

) : BaseEntity()
