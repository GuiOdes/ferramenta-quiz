package com.sistemas.ferramentaquiz.api.controller

import com.sistemas.ferramentaquiz.api.request.CreateQuizRequest
import com.sistemas.ferramentaquiz.api.request.GuestOnQuizRequest
import com.sistemas.ferramentaquiz.api.request.PlusScoreRequest
import com.sistemas.ferramentaquiz.service.GuestService
import com.sistemas.ferramentaquiz.service.JwtService
import com.sistemas.ferramentaquiz.service.QuizService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/quiz")
class QuizController(
    private val service: QuizService,
    private val jwtService: JwtService,
    private val guestService: GuestService
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

    @DeleteMapping("/guest")
    fun deleteGuest(
        @RequestHeader("Authorization") token: String,
        @RequestBody guestOnQuizRequest: GuestOnQuizRequest
    ) = guestService.removeGuest(guestOnQuizRequest, jwtService.extractUsername(token))

    @PutMapping("/score/plus")
    fun plusScore(
        @RequestHeader("Authorization") token: String,
        @Validated @RequestBody scoreRequest: PlusScoreRequest
    ) = service.plusScore(scoreRequest, jwtService.extractUsername(token))

    @GetMapping("/ranking/{quizCode}")
    fun ranking(
        @PathVariable quizCode: String
    ) = service.findRanking(quizCode)
}
