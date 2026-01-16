package com.example.nudgapi.domain.routine

import com.example.nudgapi.domain.global.BaseEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "routine_completion_logs")
class RoutineCompletionLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var userId: Long,

    @Column(nullable = false)
    var routineId: Long,

    @Column(nullable = false)
    var completionDate: LocalDate
) : BaseEntity()
