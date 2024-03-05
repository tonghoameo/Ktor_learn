package com.example.repository


import com.example.model.User
import java.util.UUID

class UserRepository {
    private val users = mutableListOf<User>()
    fun findAll(): List<User> = users
    fun findById(id: UUID): User? = users.firstOrNull{ it.id == id }
    fun findByName(name: String): User? = users.firstOrNull{ it.username == name }
    fun save(user: User): Boolean = users.add(user)
}
