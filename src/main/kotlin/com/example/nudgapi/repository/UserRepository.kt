package com.example.nudgapi.repository

import com.example.nudgapi.domain.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long>
