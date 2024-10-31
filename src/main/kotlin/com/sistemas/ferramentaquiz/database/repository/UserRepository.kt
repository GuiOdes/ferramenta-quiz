package com.sistemas.ferramentaquiz.database.repository

import com.sistemas.ferramentaquiz.database.repository.data.UserSpringDataRepository
import com.sistemas.ferramentaquiz.dto.UserDto
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepository(
    private val UserSpringDataRepository: UserSpringDataRepository
) {
    fun save(user: UserDto) = UserSpringDataRepository.save(
        user.toEntity()
    ).toDto()

    fun findAll() = UserSpringDataRepository.findAll().map { it.toDto() }

    fun findById(id: Long): Optional<UserDto> = UserSpringDataRepository.findById(id).map { it.toDto() }
}