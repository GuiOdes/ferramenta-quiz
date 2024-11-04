package com.sistemas.ferramentaquiz.database.repository

import com.sistemas.ferramentaquiz.database.entity.UserEntity
import com.sistemas.ferramentaquiz.database.repository.data.QuizSpringDataRepository
import com.sistemas.ferramentaquiz.dto.QuizDto
import com.sistemas.ferramentaquiz.dto.UserDto
import org.springframework.stereotype.Repository

@Repository
class QuizRepository(
    private val repository: QuizSpringDataRepository
) {
    fun save(quiz: QuizDto) = repository.save(
        quiz.toEntity()
    )

    fun findAllByUser(user: UserDto) = repository.findAllByUser(user.toEntity())
}