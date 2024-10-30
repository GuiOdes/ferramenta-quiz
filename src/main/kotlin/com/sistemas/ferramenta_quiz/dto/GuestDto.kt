package com.sistemas.ferramenta_quiz.dto

import com.sistemas.ferramenta_quiz.database.entity.GuestEntity

data class GuestDto(
    val id: Long? = null,
    val name: String,
    val ip: String,
    val score: Int = 0
) {

    fun toEntity() = GuestEntity(
        id = id,
        name = name,
        ip = ip,
        score = score
    )
}
