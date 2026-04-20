package com.example.nudgapi.security

import com.example.nudgapi.domain.User
import com.example.nudgapi.repository.UserRepository
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OAuth2UserService(private val userRepository: UserRepository) : OidcUserService() {

    @Transactional
    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        val oidcUser = super.loadUser(userRequest)
        val email = oidcUser.email ?: error("Email not provided by Google")
        val name = oidcUser.fullName ?: oidcUser.email!!
        println("email: $email, name: $name")
        if (!userRepository.existsByEmail(email)) {
            println("user not found, saving user")
            userRepository.save(
                User(
                    name = name,
                    email = email,
                    passwordHash = "",
                    avatarUrl = oidcUser.picture,
                )
            )
        }

        return oidcUser
    }
}
