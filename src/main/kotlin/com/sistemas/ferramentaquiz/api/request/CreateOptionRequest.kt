package com.sistemas.ferramentaquiz.api.request

import com.sistemas.ferramentaquiz.dto.OptionDto
import com.sistemas.ferramentaquiz.dto.QuestionDto
import jakarta.validation.constraints.NotBlank

data class CreateOptionRequest(
    @field:NotBlank(message = "Description is required")
    val description: String,
    @field:NotBlank(message = "IsRight is required")
    val isRight: Boolean,
    @field:NotBlank(message = "QuestionId is required")
    val questionId: Long
) {
    fun toDto(question: QuestionDto) = OptionDto(
        description = description,
        isRight = isRight,
        questionId = question.id!!
    )
}
