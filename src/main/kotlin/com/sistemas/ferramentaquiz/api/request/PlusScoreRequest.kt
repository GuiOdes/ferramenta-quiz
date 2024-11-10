package com.sistemas.ferramentaquiz.api.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class PlusScoreRequest(
    @field:Positive(message = "guestId is required")
    val guestId: Long,
    @field:NotBlank(message = "quizCode is required")
    val quizCode: String,
    @field:Positive(message = "value to plus score must be positive")
    val value: Int
)
