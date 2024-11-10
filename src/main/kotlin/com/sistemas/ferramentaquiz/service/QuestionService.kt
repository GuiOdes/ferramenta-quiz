package com.sistemas.ferramentaquiz.service

import com.sistemas.ferramentaquiz.api.request.CreateQuestionRequest
import com.sistemas.ferramentaquiz.database.repository.QuestionRepository
import com.sistemas.ferramentaquiz.database.repository.QuizRepository
import com.sistemas.ferramentaquiz.dto.QuestionDto
import com.sistemas.ferramentaquiz.dto.QuizDto
import com.sistemas.ferramentaquiz.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class QuestionService(
    private val questionRepository: QuestionRepository,
    private val quizRepository: QuizRepository
) {

    fun save(request: CreateQuestionRequest): QuestionDto {
        val quiz = quizRepository.findById(request.quizId)
            ?: throw NotFoundException(QuizDto::class)

        return questionRepository.save(request.toDto(quiz.id!!))
    }

    fun findAllByQuizId(quizId: Long): List<QuestionDto> {
        return questionRepository.findAllByQuizId(quizId)
    }
}
