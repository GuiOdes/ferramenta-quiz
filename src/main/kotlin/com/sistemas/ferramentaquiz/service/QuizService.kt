package com.sistemas.ferramentaquiz.service

import com.sistemas.ferramentaquiz.api.request.CreateQuizRequest
import com.sistemas.ferramentaquiz.database.repository.QuizRepository
import com.sistemas.ferramentaquiz.database.repository.UserRepository
import com.sistemas.ferramentaquiz.dto.CreateQuizDto
import com.sistemas.ferramentaquiz.exception.UserNotFoundException
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class QuizService(
    private val repository: QuizRepository,
    private val userRepository: UserRepository
) {
    fun save(request: CreateQuizRequest, userId: Long) {
        val user = userRepository.findById(userId)
        if(user.isEmpty) throw UserNotFoundException()

        val code = generateAcessCode()

        val dto = CreateQuizDto(request.title,
            user.get().toEntity(),
            code,
            false)

        repository.save(dto)
    }

    fun generateAcessCode(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random(System.currentTimeMillis())

        return (1..7).
            map { chars[random.nextInt(chars.length)] }.
            joinToString("")
    }
}