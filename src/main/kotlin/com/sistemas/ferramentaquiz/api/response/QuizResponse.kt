package com.sistemas.ferramentaquiz.api.response

import com.sistemas.ferramentaquiz.dto.GuestDto

data class QuizResponse(
    val id: Long? = null,
    val title: String,
    val code: String,
    val isDone: Boolean,
    val user: UserResponse,
    val guests: List<GuestDto>,
    val questions: List<QuestionResponse>
)
