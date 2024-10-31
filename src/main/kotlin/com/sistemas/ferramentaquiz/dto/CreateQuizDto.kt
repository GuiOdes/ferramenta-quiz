package com.sistemas.ferramentaquiz.dto

import com.sistemas.ferramentaquiz.database.entity.QuizEntity
import com.sistemas.ferramentaquiz.database.entity.UserEntity

data class CreateQuizDto(
    val title: String,
    val user: UserEntity,
    val code: String,
    val isDone: Boolean = false
){
    fun toEntity() = QuizEntity(
        title = title,
        code = code,
        isDone = isDone,
        user = user
    )
}