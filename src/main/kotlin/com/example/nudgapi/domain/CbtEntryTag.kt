package com.example.nudgapi.domain

import jakarta.persistence.*

@Entity
@Table(name = "cbt_entry_tags")
class CbtEntryTag(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "entry_id", nullable = false)
    val entryId: Long,

    @Column(nullable = false)
    val tag: String,
)
