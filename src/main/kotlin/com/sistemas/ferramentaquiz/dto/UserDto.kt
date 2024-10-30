package com.sistemas.ferramentaquiz.dto

import com.sistemas.ferramentaquiz.database.entity.UserEntity

class UserDto(
    val id: Long? = null,
    val name: String,
    val email: String,
    val password: String,
) {
    fun toEntity() = UserEntity(
        id = id,
        name = name,
        email = email,
        password = password
    )
}