package com.example.plugins

import io.ktor.server.application.*
import com.example.service.UserService
import com.example.service.JwtService

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity(
    jwtService: JwtService
) {

    authentication {
        jwt {
            realm = jwtService.jwtRealm
            verifier(jwtService.jwtVerifier)
            validate { credential ->
                jwtService.customValidator(credential)
            }
        }
        jwt("auth1") {

            realm = jwtService.jwtRealm
            verifier(jwtService.jwtVerifier)
            validate { credential ->
                jwtService.customValidator(credential)
            }
        }
    }
}

