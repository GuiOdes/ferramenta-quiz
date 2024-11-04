package com.sistemas.ferramentaquiz.database.repository

import com.sistemas.ferramentaquiz.database.repository.data.QuestionSpringDataRepository
import com.sistemas.ferramentaquiz.dto.QuestionDto
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class QuestionRepository(
    private val dataRepository: QuestionSpringDataRepository
) {

    fun save(question: QuestionDto): QuestionDto {
        return dataRepository.save(question.toEntity()).toDto()
    }

    fun findAllByQuizId(quizId: Long): List<QuestionDto> {
        return dataRepository.findAllByQuizId(quizId).map { it.toDto() }
    }

    fun findById(id: Long): QuestionDto? {
        return dataRepository.findById(id).getOrNull()?.toDto()
    }
}
