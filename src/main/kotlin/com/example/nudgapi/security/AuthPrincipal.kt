package com.example.nudgapi.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthPrincipal(val userId: Long, private val email: String) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> = emptyList()
    override fun getPassword(): String = ""
    override fun getUsername(): String = email
}
