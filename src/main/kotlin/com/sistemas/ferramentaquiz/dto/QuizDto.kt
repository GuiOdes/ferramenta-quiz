package com.sistemas.ferramentaquiz.dto

import com.sistemas.ferramentaquiz.api.response.QuizResponse
import com.sistemas.ferramentaquiz.database.entity.QuizEntity

class QuizDto(
    val id: Long? = null,
    val title: String,
    val code: String,
    val isDone: Boolean,
    val user: UserDto,
    val guests: List<GuestDto> = emptyList(),
    val questions: List<QuestionDto> = emptyList()
) {
    fun toEntity() = QuizEntity(
        id = id,
        title = title,
        code = code,
        isDone = isDone,
        user = user.toEntity(),
        guests = guests.map { it.toEntity() }.toMutableSet()
    )

    fun toResponse() = QuizResponse(
        id = id,
        title = title,
        code = code,
        isDone = isDone,
        user = user.toResponse(),
        guests = guests,
        questions = questions.map { it.toResponse() }
    )
}
