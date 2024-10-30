package com.sistemas.ferramentaquiz.service

import com.sistemas.ferramentaquiz.api.request.CreateUserRequest
import com.sistemas.ferramentaquiz.database.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val repository: UserRepository
) {
    fun save(request: CreateUserRequest){
//        encode password
        repository.save(request.toDto())
    }

    fun findAll() = repository.findAll()
}