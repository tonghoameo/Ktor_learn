package com.example.routing.response

import kotlinx.serialization.Serializable
import com.example.util.UUIDSerializer
import java.util.UUID

@Serializable
data class UserResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val username: String
)
