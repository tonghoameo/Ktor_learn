package com.example.service

import com.example.routing.request.LoginReq

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.application.*

import java.util.*

class JwtService (
    private val   application: Application,
    private val  userService: UserService
){
    
    fun getConfigProperty(path: String) = 
        application.environment.config.property(path).getString()
    // Please read the jwt property from the config file if you are using EngineMain
    val jwtAudience = getConfigProperty("jwt.audience")
    val jwtDomain = getConfigProperty("jwt.domain")
    val jwtRealm = getConfigProperty("jwt.realm")
    val jwtSecret = getConfigProperty("jwt.secret")

    val jwtVerifier:JWTVerifier = 
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .build()
    
    fun createJwtToken(loginReq: LoginReq): String?{
        val foundUser = userService.findByName(loginReq.username)       
        return if(foundUser != null && foundUser.password == loginReq.password){
                JWT
                    .create()
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .withClaim("username", foundUser.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 60000))    
                    .sign(Algorithm.HMAC256(jwtSecret))
            
        }else null
    }
    fun customValidator(credential: JWTCredential): JWTPrincipal? {
        val username = extractUsername(credential)
        val foundUser = username?.let(userService::findByName)
        return foundUser?.let {
            if(audiencematches(credential)){
                JWTPrincipal(credential.payload)
            }else null
        } 
    }
    private fun audiencematches(credential: JWTCredential): Boolean =
        credential.payload.audience.contains(jwtAudience)
    fun extractUsername(credential: JWTCredential): String? {
        return credential.payload.getClaim("username").asString()
    }
}
