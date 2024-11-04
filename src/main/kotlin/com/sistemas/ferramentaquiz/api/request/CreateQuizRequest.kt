package com.sistemas.ferramentaquiz.api.request

import jakarta.validation.constraints.NotBlank

data class CreateQuizRequest(
    @field:NotBlank(message = "title is required")
    val title: String
)
