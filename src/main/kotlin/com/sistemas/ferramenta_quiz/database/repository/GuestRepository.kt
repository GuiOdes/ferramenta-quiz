package com.sistemas.ferramenta_quiz.database.repository

import com.sistemas.ferramenta_quiz.database.repository.data.GuestSpringDataRepository
import com.sistemas.ferramenta_quiz.dto.GuestDto
import org.springframework.stereotype.Repository

@Repository
class GuestRepository(
    private val guestSpringDataRepository: GuestSpringDataRepository
) {

    fun save(guest: GuestDto) = guestSpringDataRepository.save(
        guest.toEntity()
    ).toDto()

    fun findAll() = guestSpringDataRepository.findAll().map { it.toDto() }
}
