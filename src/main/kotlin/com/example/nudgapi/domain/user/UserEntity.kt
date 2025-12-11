package com.example.nudgapi.domain.user

import com.example.nudgapi.domain.global.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var email: String,
    var name: String,
    @Column(nullable = true)
    var password: String? = null,
    @Column(nullable = false)
    var provider: String = "local",
    @Column(nullable = true)
    var providerId: String? = null,
    @Column(nullable = true)
    var profileImage: String? = null,
    @Column(nullable = false)
    var useYn: String = "Y"
) : BaseEntity() {
    fun deactivate() {
        useYn = "N"
    }
    fun updateProfile(name: String, profileImage: String?) {
        this.name = name
        this.profileImage = profileImage
    }
}