package com.sistemas.ferramentaquiz.api.controller

import com.sistemas.ferramentaquiz.api.request.AuthenticationRequest
import com.sistemas.ferramentaquiz.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class AuthenticationController(
    private val userService: UserService
) {

    @PostMapping("/login")
    fun login(@RequestBody authenticationRequest: AuthenticationRequest) =
        userService.generateJwtToken(authenticationRequest)
}
