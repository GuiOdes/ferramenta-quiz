package com.sistemas.ferramentaquiz.api.controller

import com.sistemas.ferramentaquiz.api.request.CreateQuizRequest
import com.sistemas.ferramentaquiz.service.JwtService
import com.sistemas.ferramentaquiz.service.QuizService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/quiz")
class QuizController(
    val service: QuizService,
    val jwtService: JwtService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody request: CreateQuizRequest, @RequestHeader("Authorization") token: String) {
        val email = jwtService.extractUsername(token)
        service.save(request, email)
    }

    @GetMapping
    fun findAll(
        @RequestHeader("Authorization") token: String
    ) = service.findAllByUserEmail(jwtService.extractUsername(token))
}
