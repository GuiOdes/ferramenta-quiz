package com.sistemas.ferramentaquiz.database.entity

import com.sistemas.ferramentaquiz.dto.UserDto
import jakarta.persistence.*

@Entity
@Table(name = "user")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
    val id: Long? = null,
    val name: String,
    val email: String,
    val password: String
){
    fun toDto() = UserDto(
        id = id,
        name = name,
        email = email,
        password = password
    )
}