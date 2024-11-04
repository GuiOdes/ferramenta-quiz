package com.sistemas.ferramentaquiz.dto

import com.sistemas.ferramentaquiz.database.entity.QuestionEntity

data class QuestionDto(
    val id: Long? = null,
    val title: String,
    val description: String,
    val quiz: QuizDto
) {

    fun toEntity() = QuestionEntity(
        id = id,
        title = title,
        description = description,
        quiz = quiz.toEntity()
    )
}
