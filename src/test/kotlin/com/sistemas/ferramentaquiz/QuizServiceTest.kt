package com.sistemas.ferramentaquiz

import com.sistemas.ferramentaquiz.api.controller.QuizController
import com.sistemas.ferramentaquiz.api.request.CreateQuizRequest
import com.sistemas.ferramentaquiz.dto.QuizDto
import com.sistemas.ferramentaquiz.dto.QuizStatus
import com.sistemas.ferramentaquiz.dto.UserDto
import com.sistemas.ferramentaquiz.service.JwtService
import com.sistemas.ferramentaquiz.service.QuizService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class QuizServiceTest {

    private val service: QuizService = mockk()
    private val jwtService: JwtService = mockk()

    private val controller = QuizController(service, jwtService, mockk())

    private val userDto = UserDto(
        id = 1,
        email = "test@example.com",
        name = "Test User",
        password = "password123"
    )

    @Test
    fun `deve criar um quiz com sucesso`() {
        val token = "Bearer validToken"
        val userEmail = userDto.email
        val request = CreateQuizRequest(title = "Quiz Test")
        val generatedCode = "ABC123"
        val quizDto = QuizDto(
            id = 1,
            title = request.title,
            code = generatedCode,
            status = QuizStatus.WAITING_GUESTS,
            user = userDto,
            guests = emptyList(),
            questions = emptyList()
        )

        every { jwtService.extractUsername(token) } returns userEmail
        every { service.generateAccessCode() } returns generatedCode
        every { service.save(request, userEmail) } returns quizDto

        controller.save(request, token)

        verify(exactly = 1) { jwtService.extractUsername(token) }
        verify(exactly = 1) { service.save(request, userEmail) }
    }
    @Test
    fun `deve lançar exceção quando o token for inválido`() {
        val token = "Bearer invalidToken"
        val request = CreateQuizRequest(title = "Quiz Test")

        every { jwtService.extractUsername(token) } throws IllegalArgumentException("Invalid token")

        val exception = assertThrows<IllegalArgumentException> {
            controller.save(request, token)
        }

        assertEquals("Invalid token", exception.message)

        verify(exactly = 1) { jwtService.extractUsername(token) }

        verify(exactly = 0) { service.save(any(), any()) }
    }
    @Test
    fun `deve lançar NotFoundException se o usuário não existir`() {
        val token = "Bearer validToken"
        val userEmail = "nonexistent@example.com"
        val request = CreateQuizRequest(title = "Quiz Test")

        every { jwtService.extractUsername(token) } returns userEmail
        every {
            service.save(request, userEmail)
        } throws com.sistemas.ferramentaquiz.exception.NotFoundException(UserDto::class)

        val exception = assertThrows<com.sistemas.ferramentaquiz.exception.NotFoundException> {
            controller.save(request, token)
        }

        assertEquals("UserDto not found!", exception.message)

        verify(exactly = 1) { jwtService.extractUsername(token) }
        verify(exactly = 1) { service.save(request, userEmail) }
    }
}
