package com.sistemas.ferramenta_quiz.service

import com.sistemas.ferramenta_quiz.api.request.CreateUserRequest
import com.sistemas.ferramenta_quiz.database.repository.UserRepository
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