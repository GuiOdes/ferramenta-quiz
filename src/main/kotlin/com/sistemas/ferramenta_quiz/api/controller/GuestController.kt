package com.sistemas.ferramenta_quiz.api.controller

import com.sistemas.ferramenta_quiz.api.request.CreateGuestRequest
import com.sistemas.ferramenta_quiz.service.GuestService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/guest")
class GuestController(
    private val guestService: GuestService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody request: CreateGuestRequest) = guestService.save(request)

    @GetMapping
    fun findAll() = guestService.findAll()
}
