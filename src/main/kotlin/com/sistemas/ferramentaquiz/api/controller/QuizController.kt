package com.sistemas.ferramentaquiz.api.controller

import com.sistemas.ferramentaquiz.api.request.CreateQuizRequest
import com.sistemas.ferramentaquiz.dto.QuizDto
import com.sistemas.ferramentaquiz.service.JwtService
import com.sistemas.ferramentaquiz.service.QuizService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/quiz")
class QuizController(
    val service: QuizService,
    val jwtService: JwtService
) {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody request: CreateQuizRequest, @RequestHeader("token") token: String) {
        val email = jwtService.extractUsername(token)
        service.save(request, email)
    }

    @GetMapping("/history")
    @ResponseStatus(HttpStatus.OK)
    fun findAllByUser(@RequestHeader("token") token: String): List<QuizDto>{
        val email = jwtService.extractUsername(token)
        return service.findAllUserQuizzes(email)
    }
}