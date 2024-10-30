package com.sistemas.ferramentaquiz.api.request

import com.sistemas.ferramentaquiz.dto.GuestDto
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
