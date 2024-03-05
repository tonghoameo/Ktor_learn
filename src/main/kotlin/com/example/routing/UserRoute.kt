package com.example.routing

import com.example.service.UserService
import com.example.routing.request.UserRequest
import com.example.routing.response.UserResponse
import com.example.model.User


import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*

import io.ktor.http.*
import java.util.UUID

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Route.userRoute(
    userService: UserService
){
    post {
        val userReq = call.receive<UserRequest>()
        println("data body $userReq.username and $userReq.password")
        //call.respondText("data body ${userReq.username} and ${userReq.password}")
        val newUser = userService.save(
            user = userReq.toModel()
        ) ?: return@post call.respond(HttpStatusCode.BadRequest)
        
        call.response.header(
            name = "id",
            value = newUser.id.toString()
        )
        call.respond(
            message = HttpStatusCode.Created
        )
    }
    authenticate("auth1") {
        get{
            val users = userService.findAll()
            call.respond(
                message = users.map(User::toResponse)
            )
        }
    }
    get("/{id}"){
        val id: String = call.parameters["id"]
            ?: return@get call.respond(HttpStatusCode.BadRequest)

        val foundUser = userService.findById(id)
            ?: return@get call.respond(HttpStatusCode.NotFound)
        call.respond(
            message = HttpStatusCode.Created
        )
    }
}

private fun UserRequest.toModel(): User = 
    User(
        id = UUID.randomUUID(),
        username = this.username,
        password = this.password
    )
private fun User.toResponse(): UserResponse = 
    UserResponse(
        id = this.id,
        username = this.username   
    )

