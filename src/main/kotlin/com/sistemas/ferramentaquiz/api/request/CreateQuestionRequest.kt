package com.sistemas.ferramentaquiz.api.request

import com.sistemas.ferramentaquiz.dto.QuestionDto
import com.sistemas.ferramentaquiz.dto.QuizDto
import jakarta.validation.constraints.NotBlank

data class CreateQuestionRequest(
    @field:NotBlank(message = "Title is required")
    val title: String,
    @field:NotBlank(message = "Description is required")
    val description: String,
    @field:NotBlank(message = "QuizId is required")
    val quizId: Long
) {

    fun toDto(quiz: QuizDto) = QuestionDto(
        title = title,
        description = description,
        quiz = quiz
    )
}
