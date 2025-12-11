package com.example.nudgapi.domain.goal

import com.example.nudgapi.domain.global.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "goals")
class Goals(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false)
    var user_id: Long,
    @Column(nullable = false)
    var title: String,
    @Column(nullable = true)
    var color: String?=null,
    @Column(nullable = true)
    var status: String?=null,
): BaseEntity() {

}