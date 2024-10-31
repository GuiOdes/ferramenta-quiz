package com.sistemas.ferramentaquiz.api.request

import jakarta.validation.constraints.NotBlank
import org.apache.catalina.User

data class CreateQuizRequest(
    @field:NotBlank(message = "title is required")
    val title: String,
    val code: String? = null
)