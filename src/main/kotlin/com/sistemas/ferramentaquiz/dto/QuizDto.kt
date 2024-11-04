package com.sistemas.ferramentaquiz.dto

import com.sistemas.ferramentaquiz.database.entity.QuizEntity
import com.sistemas.ferramentaquiz.database.entity.UserEntity

class QuizDto(
    val id: Long? = null,
    val title: String,
    val code: String,
    val isDone: Boolean,
    val user: UserEntity,
    val guests: List<GuestDto> = emptyList()
) {
    fun toEntity() = QuizEntity(
        id = id,
        title = title,
        code = code,
        isDone = isDone,
        user = user,
        guests = guests.map { it.toEntity() }.toMutableList()
    )
}
