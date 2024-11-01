package com.sistemas.ferramentaquiz.database.repository

import com.sistemas.ferramentaquiz.database.repository.data.UserSpringDataRepository
import com.sistemas.ferramentaquiz.dto.UserDto
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val userSpringDataRepository: UserSpringDataRepository
) {
    fun save(user: UserDto) = userSpringDataRepository.save(
        user.toEntity()
    ).toDto()

    fun findAll() = userSpringDataRepository.findAll().map { it.toDto() }

    fun findByEmail(email: String) = userSpringDataRepository.findByEmail(email)?.toDto()
}
