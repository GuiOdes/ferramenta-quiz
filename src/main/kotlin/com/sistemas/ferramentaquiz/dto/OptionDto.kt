package com.sistemas.ferramentaquiz.dto

import com.sistemas.ferramentaquiz.database.entity.OptionEntity

data class OptionDto(
    val id: Long? = null,
    val description: String,
    val isRight: Boolean,
    val question: QuestionDto
) {

    fun toEntity() = OptionEntity(
        id = id,
        description = description,
        isRight = isRight,
        question = question.toEntity()
    )
}
