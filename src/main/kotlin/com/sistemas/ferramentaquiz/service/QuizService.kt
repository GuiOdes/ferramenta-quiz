package com.sistemas.ferramentaquiz.service

import com.sistemas.ferramentaquiz.api.request.CreateQuizRequest
import com.sistemas.ferramentaquiz.database.repository.QuizRepository
import com.sistemas.ferramentaquiz.database.repository.UserRepository
import com.sistemas.ferramentaquiz.dto.QuizDto
import com.sistemas.ferramentaquiz.exception.UserNotFoundException
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Service

@Service
class QuizService(
    private val repository: QuizRepository,
    private val userRepository: UserRepository
) {
    fun save(request: CreateQuizRequest, userEmail: String) {
        val user = userRepository.findByEmail(userEmail) ?: throw UserNotFoundException()
        val code = generateAcessCode()

        val dto = QuizDto(
            title = request.title,
            user = user.toEntity(),
            code = code,
            isDone = false)

        repository.save(dto)
    }

    fun generateAcessCode(): String = RandomStringUtils.secure().nextAlphanumeric(7);

    fun findAllUserQuizzes(email: String): List<QuizDto>{
        val user = userRepository.findByEmail(email) ?: throw UserNotFoundException()
        return repository.findAllByUser(user).map { it.toDto() }
    }
}