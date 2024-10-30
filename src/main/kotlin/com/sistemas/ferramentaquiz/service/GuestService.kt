package com.sistemas.ferramentaquiz.service

import com.sistemas.ferramentaquiz.api.request.CreateGuestRequest
import com.sistemas.ferramentaquiz.database.repository.GuestRepository
import org.springframework.stereotype.Service

@Service
class GuestService(
    private val repository: GuestRepository
) {

    fun save(request: CreateGuestRequest) = repository.save(request.toDto())

    fun findAll() = repository.findAll()
}
