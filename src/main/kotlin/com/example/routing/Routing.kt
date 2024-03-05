package com.example.routing

import com.example.service.UserService
import com.example.service.JwtService

import io.ktor.server.routing.*
import io.ktor.server.application.*


fun Application.configureRouting(
    userService : UserService,
    jwtService: JwtService
){
    routing{
        route("/api/user"){
            userRoute(userService)
        }
        route("/api/auth"){
            authRoute(jwtService)
        }
    }
}
