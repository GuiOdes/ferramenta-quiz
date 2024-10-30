package com.sistemas.ferramentaquiz.api.controller

import com.sistemas.ferramentaquiz.api.request.CreateUserRequest
import com.sistemas.ferramentaquiz.service.UserService
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