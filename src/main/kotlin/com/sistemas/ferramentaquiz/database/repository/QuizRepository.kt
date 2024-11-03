package com.sistemas.ferramentaquiz.database.repository

import com.sistemas.ferramentaquiz.database.repository.data.QuizSpringDataRepository
import com.sistemas.ferramentaquiz.dto.QuizDto
import org.springframework.stereotype.Repository

@Repository
class QuizRepository(
    private val repository: QuizSpringDataRepository
) {
    fun save(quiz: QuizDto) = repository.save(
        quiz.toEntity()
    )
}