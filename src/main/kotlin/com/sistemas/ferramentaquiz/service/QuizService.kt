package com.sistemas.ferramentaquiz.service

import com.sistemas.ferramentaquiz.api.request.CreateQuizRequest
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
    private val accessCodeLength: Int
) {
    fun save(request: CreateQuizRequest, userEmail: String) {
        val user = userRepository.findByEmail(userEmail) ?: throw NotFoundException(UserDto::class)
        val code = generateAccessCode()

        val dto = QuizDto(
            title = request.title,
            user = user.toEntity(),
            code = code,
            isDone = false
        )

        repository.save(dto)
    }

    fun findAllUserQuizzes(email: String): List<QuizDto>{
        val user = userRepository.findByEmail(email) ?: throw UserNotFoundException()
        return repository.findAllByUser(user).map { it.toDto() }
    }
    
    fun generateAccessCode(): String = RandomStringUtils.secure().nextAlphanumeric(accessCodeLength)

    fun findAllByUserEmail(userEmail: String) = repository.findAllByUserId(userEmail)
}