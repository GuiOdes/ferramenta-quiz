package com.sistemas.ferramenta_quiz.database.repository

import com.sistemas.ferramenta_quiz.database.repository.data.UserSpringDataRepository
import com.sistemas.ferramenta_quiz.dto.UserDto
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val UserSpringDataRepository: UserSpringDataRepository
) {
    fun save(user: UserDto) = UserSpringDataRepository.save(
        user.toEntity()
    ).toDto()

    fun findAll() = UserSpringDataRepository.findAll().map { it.toDto() }
}