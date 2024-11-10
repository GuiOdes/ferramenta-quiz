package com.sistemas.ferramentaquiz.api.controller

import com.sistemas.ferramentaquiz.api.request.CreateQuestionRequest
import com.sistemas.ferramentaquiz.service.QuestionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/question")
class QuestionController(
    private val questionService: QuestionService
) {

    @PostMapping
    fun save(@RequestBody request: CreateQuestionRequest) = questionService.save(request)

    @GetMapping("/quiz/{quizId}")
    fun findAllByQuizId(@PathVariable quizId: Long) = questionService.findAllByQuizId(quizId)
}
