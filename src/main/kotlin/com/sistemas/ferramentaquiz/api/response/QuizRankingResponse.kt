package com.sistemas.ferramentaquiz.api.response

data class GuestRankingResponse(
    val guestName: String,
    val score: Int,
    val position: Int
)

data class QuizRankingResponse(
    val guestRanking: List<GuestRankingResponse>,
    val winner: GuestRankingResponse
)
