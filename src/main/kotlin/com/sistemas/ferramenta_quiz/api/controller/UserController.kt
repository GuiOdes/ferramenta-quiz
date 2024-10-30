package com.sistemas.ferramenta_quiz.api.controller

import com.sistemas.ferramenta_quiz.api.request.CreateUserRequest
import com.sistemas.ferramenta_quiz.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController("/user")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(request: CreateUserRequest) = userService.save(request)

    @GetMapping
    fun findAll() = userService.findAll()
}