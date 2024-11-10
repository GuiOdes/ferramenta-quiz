package com.sistemas.ferramentaquiz.dto

import com.sistemas.ferramentaquiz.database.entity.OptionEntity
import com.sistemas.ferramentaquiz.database.entity.QuestionEntity

data class OptionDto(
    val id: Long? = null,
    val description: String,
    val isRight: Boolean,
    val questionId: Long
) {

    fun toEntity(questionEntity: QuestionEntity) = OptionEntity(
        id = id,
        description = description,
        isRight = isRight,
        question = questionEntity
    )
}
