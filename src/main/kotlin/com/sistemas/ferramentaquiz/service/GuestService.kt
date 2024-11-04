package com.sistemas.ferramentaquiz.service

import com.sistemas.ferramentaquiz.api.request.CreateGuestRequest
import com.sistemas.ferramentaquiz.api.request.GuestOnQuizRequest
import com.sistemas.ferramentaquiz.database.repository.GuestRepository
import com.sistemas.ferramentaquiz.database.repository.QuizRepository
import com.sistemas.ferramentaquiz.dto.QuizDto
import com.sistemas.ferramentaquiz.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class GuestService(
    private val repository: GuestRepository,
    private val quizRepository: QuizRepository
) {
    fun save(request: CreateGuestRequest) = repository.save(request.toDto())

    fun findAll() = repository.findAll()

    fun joinInQuiz(request: GuestOnQuizRequest) = quizRepository.addGuest(request)

    fun removeGuest(request: GuestOnQuizRequest, requestEmail: String) {
        quizRepository.findByCode(request.quizCode)?.let {
            require(it.user.email == requestEmail) { "You are not the owner of this quiz" }
            quizRepository.removeGuest(request)
        } ?: throw NotFoundException(QuizDto::class)
    }
}
