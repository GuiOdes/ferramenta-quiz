package com.sistemas.ferramentaquiz.api.request

import jakarta.validation.constraints.NotBlank

data class AuthenticationRequest(
    @NotBlank(message = "Essa informação é obrigatória")
    val username: String,
    @NotBlank(message = "Essa informação é obrigatória")
    val password: String
)
