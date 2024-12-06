package com.sistemas.ferramentaquiz.api.controller

import com.sistemas.ferramentaquiz.api.request.CreateOptionRequest
import com.sistemas.ferramentaquiz.api.request.SumHitOptionRequest
import com.sistemas.ferramentaquiz.service.OptionService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/option")
class OptionController(
    private val optionService: OptionService
) {

    @PostMapping
    fun save(@RequestBody request: CreateOptionRequest) = optionService.save(request)

    @GetMapping("/question/{questionId}")
    fun findAllByQuestionId(@PathVariable questionId: Long) = optionService.findAllByQuestionId(questionId)

    @PutMapping("/sum-count")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun sumCount(@RequestBody sumHitOptionRequest: SumHitOptionRequest) = optionService.sumCount(sumHitOptionRequest)
}
