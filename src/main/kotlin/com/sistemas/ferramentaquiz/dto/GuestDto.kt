package com.sistemas.ferramentaquiz.dto

import com.sistemas.ferramentaquiz.api.response.GuestRankingResponse
import com.sistemas.ferramentaquiz.database.entity.GuestEntity

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

    fun toRankingResponse(position: Int) = GuestRankingResponse(
        guestName = name,
        score = score,
        position = position
    )
}
