package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

import kotlinx.serialization.*
import kotlinx.serialization.json.*


@Serializable
data class Customer(
    val id: Int,
    val name: String,
    val password: String
)

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/customer"){
            val rawData = call.receive<Customer>()
            call.respondText("success \n data : [$rawData]")
        
        }
    }
}
