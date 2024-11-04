package com.sistemas.ferramentaquiz.database.repository

import com.sistemas.ferramentaquiz.database.repository.data.QuizSpringDataRepository
import com.sistemas.ferramentaquiz.dto.QuizDto
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class QuizRepository(
    private val repository: QuizSpringDataRepository
) {
    fun save(quiz: QuizDto) = repository.save(
        quiz.toEntity()
    )

    fun findById(id: Long) = repository.findById(id).getOrNull()?.toDto()

    fun findAllByUserId(userId: String) = repository.findAllByUserEmail(userId).map { it.toDto() }
}
