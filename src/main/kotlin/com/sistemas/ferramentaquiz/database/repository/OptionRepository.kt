package com.sistemas.ferramentaquiz.database.repository

import com.sistemas.ferramentaquiz.database.repository.data.OptionSpringDataRepository
import com.sistemas.ferramentaquiz.dto.OptionDto
import org.springframework.stereotype.Repository

@Repository
class OptionRepository(
    private val dataRepository: OptionSpringDataRepository
) {

    fun save(option: OptionDto): OptionDto {
        return dataRepository.save(option.toEntity()).toDto()
    }

    fun findAllByQuestionId(questionId: Long): List<OptionDto> {
        return dataRepository.findAllByQuestionId(questionId).map { it.toDto() }
    }
}
