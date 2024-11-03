package com.sistemas.ferramentaquiz.service

import com.sistemas.ferramentaquiz.api.request.CreateQuizRequest
import com.sistemas.ferramentaquiz.database.repository.QuizRepository
import com.sistemas.ferramentaquiz.database.repository.UserRepository
import com.sistemas.ferramentaquiz.dto.CreateQuizDto
import com.sistemas.ferramentaquiz.exception.UserNotFoundException
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class QuizService(
    private val repository: QuizRepository,
    private val userRepository: UserRepository
) {
    fun save(request: CreateQuizRequest, userEmail: String) {
        val user = userRepository.findByEmail(userEmail) ?: throw UserNotFoundException()
        val code = generateAcessCode()

        val dto = CreateQuizDto(request.title,
            user.toEntity(),
            code,
            false)

        repository.save(dto)
    }

    fun generateAcessCode(): String = RandomStringUtils.secure().nextAlphanumeric(7);
}