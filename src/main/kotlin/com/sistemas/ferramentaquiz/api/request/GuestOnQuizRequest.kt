package com.sistemas.ferramentaquiz.api.request

data class GuestOnQuizRequest(
    val guestId: Long,
    val quizCode: String
)
