package com.sistemas.ferramenta_quiz.api.request

import com.sistemas.ferramenta_quiz.dto.GuestDto
import jakarta.validation.constraints.NotBlank

data class CreateGuestRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:NotBlank(message = "IP is required")
    val ip: String
) {
    fun toDto() = GuestDto(
        name = name,
        ip = ip
    )
}
