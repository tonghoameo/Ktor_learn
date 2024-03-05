package com.example.routing

import com.example.service.JwtService
import com.example.routing.request.LoginReq
import com.example.model.User


import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*

import io.ktor.http.*
import java.util.UUID


fun Route.authRoute(
    jwtService: JwtService
){
    post {
        val loginReq: LoginReq = call.receive<LoginReq>()
        val token = jwtService.createJwtToken(loginReq)
        token?.let{
            call.respond(
                hashMapOf("token" to it)
            )
        }?: call.respond(HttpStatusCode.Unauthorized)
            
    }

    get("/{id}"){
    }
}


