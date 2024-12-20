package com.sistemas.ferramentaquiz.dto

import com.sistemas.ferramentaquiz.api.response.QuizResponse
import com.sistemas.ferramentaquiz.database.entity.QuizEntity

data class QuizDto(
    val id: Long? = null,
    val title: String,
    val code: String,
    val status: QuizStatus,
    val user: UserDto,
    val guests: List<GuestDto> = emptyList(),
    val questions: List<QuestionDto> = emptyList()
) {
    fun toEntity() = QuizEntity(
        id = id,
        title = title,
        code = code,
        user = user.toEntity(),
        guests = guests.map { it.toEntity() }.toMutableSet(),
        status = status
    )

    fun toResponse() = QuizResponse(
        id = id,
        title = title,
        code = code,
        user = user.toResponse(),
        guests = guests,
        questions = questions.map { it.toResponse() },
        status = status
    )
}
