package com.sistemas.ferramentaquiz.api.request

import com.sistemas.ferramentaquiz.dto.UserDto
import jakarta.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,
    @field:NotBlank(message = "Email is required")
    val email: String,
    @field:NotBlank(message = "Password is required")
    val password: String
) {
    fun toDto() = UserDto(
        name = name,
        email = email,
        password = password
    )
}
