package com.sistemas.ferramentaquiz.database.repository

import com.sistemas.ferramentaquiz.database.entity.QuizEntity
import com.sistemas.ferramentaquiz.database.repository.data.QuestionSpringDataRepository
import com.sistemas.ferramentaquiz.database.repository.data.QuizSpringDataRepository
import com.sistemas.ferramentaquiz.dto.QuestionDto
import com.sistemas.ferramentaquiz.exception.NotFoundException
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class QuestionRepository(
    private val dataRepository: QuestionSpringDataRepository,
    private val quizSpringDataRepository: QuizSpringDataRepository
) {

    fun save(question: QuestionDto): QuestionDto {
        val quiz = quizSpringDataRepository.findById(question.quizId)
            .orElseThrow { throw NotFoundException(QuizEntity::class) }

        return dataRepository.save(question.toEntity(quiz)).toDto()
    }

    fun findAllByQuizId(quizId: Long): List<QuestionDto> {
        return dataRepository.findAllByQuizId(quizId).map { it.toDto() }
    }

    fun findById(id: Long): QuestionDto? {
        return dataRepository.findById(id).getOrNull()?.toDto()
    }
}
