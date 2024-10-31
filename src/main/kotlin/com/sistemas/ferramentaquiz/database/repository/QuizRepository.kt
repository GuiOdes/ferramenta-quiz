package com.sistemas.ferramentaquiz.database.repository

import com.sistemas.ferramentaquiz.database.repository.data.QuizSpringDataRepository
import com.sistemas.ferramentaquiz.dto.CreateQuizDto
import org.springframework.stereotype.Repository

@Repository
class QuizRepository(
    private val repository: QuizSpringDataRepository
) {
    fun save(quiz: CreateQuizDto) = repository.save(
        quiz.toEntity()
    )
}