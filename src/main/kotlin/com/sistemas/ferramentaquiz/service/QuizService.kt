package com.sistemas.ferramentaquiz.service

import com.guiodes.dizimum.domain.exception.ForbiddenException
import com.sistemas.ferramentaquiz.api.request.CreateQuizRequest
import com.sistemas.ferramentaquiz.api.request.PlusScoreRequest
import com.sistemas.ferramentaquiz.api.response.QuizRankingResponse
import com.sistemas.ferramentaquiz.database.repository.GuestRepository
import com.sistemas.ferramentaquiz.database.repository.QuizRepository
import com.sistemas.ferramentaquiz.database.repository.UserRepository
import com.sistemas.ferramentaquiz.dto.QuizDto
import com.sistemas.ferramentaquiz.dto.UserDto
import com.sistemas.ferramentaquiz.exception.NotFoundException
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class QuizService(
    private val repository: QuizRepository,
    private val userRepository: UserRepository,
    @Value("\${access.code.length}")
    private val accessCodeLength: Int,
    private val guestRepository: GuestRepository
) {
    fun save(request: CreateQuizRequest, userEmail: String) {
        val user = userRepository.findByEmail(userEmail) ?: throw NotFoundException(UserDto::class)
        val code = generateAccessCode()

        val dto = QuizDto(
            title = request.title,
            user = user,
            code = code,
            isDone = false
        )

        repository.save(dto)
    }

    fun generateAccessCode(): String = RandomStringUtils.secure().nextAlphanumeric(accessCodeLength).uppercase()

    fun findAllByUserEmail(userEmail: String) = repository.findAllByUserId(userEmail).map { it.toResponse() }

    fun plusScore(scoreRequest: PlusScoreRequest, userEmail: String) {
        val quiz = repository.findByCode(scoreRequest.quizCode) ?: throw NotFoundException(QuizDto::class)

        if (!isUserOwnerOfQuiz(quiz, userEmail)) {
            throw ForbiddenException("User is not the owner of the quiz")
        }

        guestRepository.plusScore(scoreRequest)
    }

    fun findRanking(quizCode: String): QuizRankingResponse {
        val quiz = repository.findByCode(quizCode) ?: throw NotFoundException(QuizDto::class)

        val guestRanking = quiz.guests.sortedByDescending { it.score }.mapIndexed { index, guest ->
            guest.toRankingResponse(index + 1)
        }

        return QuizRankingResponse(
            guestRanking = guestRanking,
            winner = guestRanking.last()
        )
    }

    fun isUserOwnerOfQuiz(quizDto: QuizDto, userEmail: String): Boolean {
        return quizDto.user.email == userEmail
    }
}
