package com.sistemas.ferramentaquiz.database.repository

import com.guiodes.dizimum.domain.exception.BadRequestException
import com.sistemas.ferramentaquiz.api.request.GuestOnQuizRequest
import com.sistemas.ferramentaquiz.database.entity.GuestEntity
import com.sistemas.ferramentaquiz.database.entity.QuizEntity
import com.sistemas.ferramentaquiz.database.repository.data.GuestSpringDataRepository
import com.sistemas.ferramentaquiz.database.repository.data.QuizSpringDataRepository
import com.sistemas.ferramentaquiz.dto.QuizDto
import com.sistemas.ferramentaquiz.exception.NotFoundException
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class QuizRepository(
    private val repository: QuizSpringDataRepository,
    private val guestSpringDataRepository: GuestSpringDataRepository
) {
    fun save(quiz: QuizDto) = repository.save(
        quiz.toEntity()
    )

    fun findById(id: Long) = repository.findById(id).getOrNull()?.toDto()

    fun findByCode(code: String) = repository.findActiveByCode(code)?.toDto()

    fun findAllByUserId(userId: String) = repository.findAllByUserEmail(userId).map { it.toDto() }

    fun removeGuest(guestOnQuizRequest: GuestOnQuizRequest) {
        val quiz = repository.findActiveByCode(guestOnQuizRequest.quizCode)
            ?: throw NotFoundException(QuizEntity::class)

        quiz.guests.find { it.id == guestOnQuizRequest.guestId }?.let {
            quiz.guests.remove(it)
        } ?: throw BadRequestException("Guest not found on quiz")

        repository.save(quiz)
    }

    fun addGuest(guestOnQuizRequest: GuestOnQuizRequest) {
        val quiz = repository.findActiveByCode(guestOnQuizRequest.quizCode)
            ?: throw NotFoundException(QuizEntity::class)
        val guest = guestSpringDataRepository.findById(guestOnQuizRequest.guestId).getOrNull()
            ?: throw NotFoundException(GuestEntity::class)

        quiz.guests.find { it.id == guestOnQuizRequest.guestId }?.let {
            throw BadRequestException("Guest already on quiz")
        }

        quiz.guests.add(guest)

        repository.save(quiz)
    }
}
