package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

import com.example.plugins.configureSerialization
import com.example.routing.configureRouting

import com.example.service.UserService
import com.example.service.JwtService
import com.example.repository.UserRepository

/*
fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}
*/

fun Application.main() {

    val userRepository = UserRepository()
    val userService = UserService(userRepository)
    val jwtService = JwtService(this,userService)
    configureSerialization()
    configureHTTP()
    configureSecurity(jwtService)
    configureRouting()
    configureRouting(userService, jwtService)
}
