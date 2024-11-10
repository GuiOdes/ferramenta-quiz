package com.sistemas.ferramentaquiz.dto

import com.sistemas.ferramentaquiz.api.response.QuestionResponse
import com.sistemas.ferramentaquiz.database.entity.QuestionEntity
import com.sistemas.ferramentaquiz.database.entity.QuizEntity

data class QuestionDto(
    val id: Long? = null,
    val title: String,
    val description: String,
    val quizId: Long
) {

    fun toEntity(quizEntity: QuizEntity) = QuestionEntity(
        id = id,
        title = title,
        description = description,
        quiz = quizEntity
    )

    fun toResponse() = QuestionResponse(
        id = id!!,
        title = title,
        description = description
    )
}
