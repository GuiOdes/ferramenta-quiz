package com.sistemas.ferramentaquiz.database.repository

import com.sistemas.ferramentaquiz.api.request.PlusScoreRequest
import com.sistemas.ferramentaquiz.database.repository.data.GuestSpringDataRepository
import com.sistemas.ferramentaquiz.dto.GuestDto
import org.springframework.stereotype.Repository

@Repository
class GuestRepository(
    private val guestSpringDataRepository: GuestSpringDataRepository
) {

    fun save(guest: GuestDto) = guestSpringDataRepository.save(
        guest.toEntity()
    ).toDto()

    fun findAll() = guestSpringDataRepository.findAll().map { it.toDto() }

    fun plusScore(scoreRequest: PlusScoreRequest) {
        val guest = guestSpringDataRepository.findById(scoreRequest.guestId).orElseThrow()

        guest.score += scoreRequest.value

        guestSpringDataRepository.save(guest)
    }
}
