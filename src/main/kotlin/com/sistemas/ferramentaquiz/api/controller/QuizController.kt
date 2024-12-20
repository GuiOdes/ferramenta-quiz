package com.sistemas.ferramentaquiz.api.controller

import com.sistemas.ferramentaquiz.api.request.CreateQuizRequest
import com.sistemas.ferramentaquiz.api.request.GuestOnQuizRequest
import com.sistemas.ferramentaquiz.api.request.PlusScoreRequest
import com.sistemas.ferramentaquiz.api.request.UpdateQuizRequest
import com.sistemas.ferramentaquiz.database.entity.QuizEntity
import com.sistemas.ferramentaquiz.dto.QuizDto
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
    fun save(@RequestBody request: CreateQuizRequest, @RequestHeader("Authorization") token: String): QuizDto {
        val email = jwtService.extractUsername(token)
        return service.save(request, email)
    }

    @PutMapping
    fun update(@RequestBody request: UpdateQuizRequest, @RequestHeader("Authorization") token: String): QuizEntity {
        val email = jwtService.extractUsername(token)
        return service.update(request, email)
    }

    @GetMapping
    fun findAll(
        @RequestHeader("Authorization") token: String
    ) = service.findAllByUserEmail(jwtService.extractUsername(token))

    @GetMapping("/code/{code}")
    fun findByCode(
        @PathVariable code: String
    ) = service.findByCode(code)

    @DeleteMapping("/guest")
    fun deleteGuest(
        @RequestHeader("Authorization") token: String,
        @RequestBody guestOnQuizRequest: GuestOnQuizRequest
    ) = guestService.removeGuest(guestOnQuizRequest, jwtService.extractUsername(token))

    @PutMapping("/score/plus")
    fun plusScore(
        @Validated @RequestBody scoreRequest: PlusScoreRequest
    ) = service.plusScore(scoreRequest)

    @GetMapping("/ranking/{quizCode}")
    fun ranking(
        @PathVariable quizCode: String
    ) = service.findRanking(quizCode)
}
