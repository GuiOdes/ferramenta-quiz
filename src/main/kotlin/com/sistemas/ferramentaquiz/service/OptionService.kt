package com.sistemas.ferramentaquiz.service

import com.guiodes.dizimum.domain.exception.BadRequestException
import com.sistemas.ferramentaquiz.api.request.CreateOptionRequest
import com.sistemas.ferramentaquiz.database.repository.OptionRepository
import com.sistemas.ferramentaquiz.database.repository.QuestionRepository
import com.sistemas.ferramentaquiz.dto.OptionDto
import com.sistemas.ferramentaquiz.exception.NotFoundException
import org.aspectj.weaver.patterns.TypePatternQuestions.Question
import org.springframework.stereotype.Service

@Service
class OptionService(
    private val optionRepository: OptionRepository,
    private val questionRepository: QuestionRepository
) {

    fun save(option: CreateOptionRequest): OptionDto {
        val question = questionRepository.findById(option.questionId)
            ?: throw NotFoundException(Question::class)

        optionRepository.findAllByQuestionId(question.id!!).firstOrNull { it.isRight }?.also {
            if (option.isRight) {
                throw BadRequestException("Question already has a right option")
            }
        }

        return optionRepository.save(option.toDto(question))
    }

    fun findAllByQuestionId(questionId: Long): List<OptionDto> {
        return optionRepository.findAllByQuestionId(questionId)
    }
}
