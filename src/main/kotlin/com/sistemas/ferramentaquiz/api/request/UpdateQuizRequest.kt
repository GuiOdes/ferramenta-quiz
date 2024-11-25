package com.sistemas.ferramentaquiz.api.request

import com.sistemas.ferramentaquiz.dto.QuizStatus
import jakarta.validation.constraints.NotBlank

data class UpdateQuizRequest(
    @field:NotBlank(message = "ID is required")
    val id: Long,
    val title: String?,
    val status: QuizStatus?
)
