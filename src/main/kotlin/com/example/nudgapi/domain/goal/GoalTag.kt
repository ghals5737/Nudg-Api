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
    // [수정] goal_id -> goalId (DB 컬럼명은 name 속성으로 지정)
    @Column(name = "goal_id", nullable = false)
    var goalId: Long,

    // [수정] tag_name -> tagName (통일성을 위해 같이 수정 권장)
    @Column(name = "tag_name", nullable = false)
    var tagName: String,
): BaseEntity() {
}