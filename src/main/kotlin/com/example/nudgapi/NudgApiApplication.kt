package com.example.nudgapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class NudgApiApplication

fun main(args: Array<String>) {
    runApplication<NudgApiApplication>(*args)
}
