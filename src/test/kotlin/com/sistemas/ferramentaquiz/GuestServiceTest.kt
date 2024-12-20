package com.sistemas.ferramentaquiz

import com.sistemas.ferramentaquiz.api.request.CreateGuestRequest
import com.sistemas.ferramentaquiz.api.request.GuestOnQuizRequest
import com.sistemas.ferramentaquiz.database.repository.GuestRepository
import com.sistemas.ferramentaquiz.database.repository.QuizRepository
import com.sistemas.ferramentaquiz.dto.QuizDto
import com.sistemas.ferramentaquiz.dto.QuizStatus
import com.sistemas.ferramentaquiz.dto.UserDto
import com.sistemas.ferramentaquiz.service.GuestService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class GuestServiceTest {
    private var guestService: GuestService

    private val guestRepository: GuestRepository = mockk()
    private val quizRepository: QuizRepository = mockk()

    init {
        guestService = GuestService(guestRepository, quizRepository)
    }

    private val userDto = UserDto(
        id = 1,
        email = "test@example.com",
        name = "Test User",
        password = "password123"
    )

    private val validQuizId = 1L

    private val validQuiz = QuizDto(
        id = validQuizId,
        title = "Valid Quiz",
        code = "ABC123",
        status = QuizStatus.IN_PROGRESS,
        user = userDto,
        guests = emptyList(),
        questions = emptyList()
    )

    @Test
    fun `deve criar um convidado quando os dados forem válidos`() {
        // Arrange
        val createGuestRequest = CreateGuestRequest(name = "John Doe", ip = "192.168.1.1", profileUrl = "link")
        val guestDto = createGuestRequest.toDto()

        // Mock do repositório para simular a criação do convidado
        every { guestRepository.save(any()) } returns guestDto

        // Act
        val savedGuest = guestService.save(createGuestRequest)

        // Assert
        assertNotNull(savedGuest)
        assertEquals("John Doe", savedGuest.name)
        assertEquals("192.168.1.1", savedGuest.ip)

        // Verificar que o repositório foi chamado para salvar o convidado
        verify(exactly = 1) { guestRepository.save(any()) }
    }

    @Test
    fun `deve permitir que um convidado se junte a um quiz`() {
        // Arrange
        val guestOnQuizRequest = GuestOnQuizRequest(guestId = 1L, quizCode = "ABC123")
        // Mock da adição do convidado ao quiz
        every { quizRepository.addGuest(guestOnQuizRequest) } returns validQuiz.toEntity()

        // Act
        guestService.joinInQuiz(guestOnQuizRequest)

        // Assert
        // Verificar que o repositório de quiz foi chamado para adicionar o convidado
        verify(exactly = 1) { quizRepository.addGuest(guestOnQuizRequest) }
    }

    @Test
    fun `deve criar um convidado com dados válidos`() {
        // Arrange
        val createGuestRequest = CreateGuestRequest(name = "John Doe", ip = "192.168.1.1", profileUrl = "link")
        val guestDto = createGuestRequest.toDto()

        // Mock do repositório para simular a criação do convidado
        every { guestRepository.save(any()) } returns guestDto

        // Act
        val savedGuest = guestService.save(createGuestRequest)

        // Assert
        assertNotNull(savedGuest)
        assertEquals("John Doe", savedGuest.name)
        assertEquals("192.168.1.1", savedGuest.ip)

        // Verificar que o repositório foi chamado para salvar o convidado
        verify(exactly = 1) { guestRepository.save(any()) }
    }
}
