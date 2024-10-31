package com.sistemas.ferramentaquiz.api.controller

import com.sistemas.ferramentaquiz.api.request.CreateQuizRequest
import com.sistemas.ferramentaquiz.service.QuizService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/quiz")
class QuizController(
    val service: QuizService
) {


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody request: CreateQuizRequest, @RequestHeader("token") token: String) {
//      Extract Id from Token
//        service.save(quizRequest /*userId*/)
    }
}