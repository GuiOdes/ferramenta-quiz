package com.sistemas.ferramenta_quiz.service

import com.sistemas.ferramenta_quiz.api.request.CreateGuestRequest
import com.sistemas.ferramenta_quiz.database.repository.GuestRepository
import org.springframework.stereotype.Service

@Service
class GuestService(
    private val repository: GuestRepository
) {

    fun save(request: CreateGuestRequest) = repository.save(request.toDto())

    fun findAll() = repository.findAll()
}
