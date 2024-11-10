package com.sistemas.ferramentaquiz.database.repository

import com.sistemas.ferramentaquiz.database.entity.QuizEntity
import com.sistemas.ferramentaquiz.database.repository.data.OptionSpringDataRepository
import com.sistemas.ferramentaquiz.database.repository.data.QuestionSpringDataRepository
import com.sistemas.ferramentaquiz.dto.OptionDto
import com.sistemas.ferramentaquiz.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class OptionRepository(
    private val dataRepository: OptionSpringDataRepository,
    private val questionSpringDataRepository: QuestionSpringDataRepository
) {

    fun save(option: OptionDto): OptionDto {
        val question = questionSpringDataRepository.findByIdOrNull(option.questionId)
            ?: throw NotFoundException(QuizEntity::class)

        return dataRepository.save(option.toEntity(question)).toDto()
    }

    fun findAllByQuestionId(questionId: Long): List<OptionDto> {
        return dataRepository.findAllByQuestionId(questionId).map { it.toDto() }
    }
}
