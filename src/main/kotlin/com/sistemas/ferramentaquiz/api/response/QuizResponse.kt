package com.sistemas.ferramentaquiz.api.response

import com.sistemas.ferramentaquiz.dto.GuestDto
import com.sistemas.ferramentaquiz.dto.QuizStatus

data class QuizResponse(
    val id: Long? = null,
    val title: String,
    val code: String,
    val user: UserResponse,
    val guests: List<GuestDto>,
    val questions: List<QuestionResponse>,
    val status: QuizStatus
)
