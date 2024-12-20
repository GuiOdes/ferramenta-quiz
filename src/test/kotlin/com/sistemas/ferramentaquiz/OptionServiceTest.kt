package com.sistemas.ferramentaquiz

import com.sistemas.ferramentaquiz.api.request.CreateOptionRequest
import com.sistemas.ferramentaquiz.database.repository.OptionRepository
import com.sistemas.ferramentaquiz.database.repository.QuestionRepository
import com.sistemas.ferramentaquiz.dto.OptionDto
import com.sistemas.ferramentaquiz.dto.QuestionDto
import com.sistemas.ferramentaquiz.exception.NotFoundException
import com.sistemas.ferramentaquiz.service.OptionService
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OptionServiceTest {
    private val optionRepository: OptionRepository = mockk()
    private val questionRepository: QuestionRepository = mockk()

    private val optionService = OptionService(optionRepository, questionRepository)

    private val validQuestionId: Long = 123L
    private val validQuestion =
        QuestionDto(id = validQuestionId, title = "Sample Question", description = "Sample Description", quizId = 1)

    private val optionDto =
        OptionDto(id = 1, description = "Sample Option", isRight = false, questionId = validQuestionId)
    private val createOptionRequest =
        CreateOptionRequest(description = "Sample Option", isRight = false, questionId = validQuestionId)

    @BeforeEach
    fun setup() {
        clearMocks(optionRepository, questionRepository)
    }
    @Test
    fun `deve salvar uma opção com sucesso`() {

        every { questionRepository.findById(validQuestionId) } returns validQuestion
        every { optionRepository.findAllByQuestionId(validQuestionId) } returns emptyList()
        every { optionRepository.save(any<OptionDto>()) } returns optionDto

        val savedOption = optionService.save(createOptionRequest)

        assertNotNull(savedOption)
        assertEquals("Sample Option", savedOption.description)
        assertFalse(savedOption.isRight)

        verify(exactly = 1) { questionRepository.findById(validQuestionId) }
        verify(exactly = 1) { optionRepository.save(any<OptionDto>()) }
    }
    @Test
    fun `deve lançar NotFoundException quando a questão não for encontrada`() {

        every { questionRepository.findById(validQuestionId) } returns null

        val exception = assertThrows<NotFoundException> {
            optionService.save(createOptionRequest)
        }

        assertEquals("Question not found!", exception.message)

        verify(exactly = 1) { questionRepository.findById(validQuestionId) }
        verify(exactly = 0) { optionRepository.save(any<OptionDto>()) }
    }
    @Test
    fun `deve salvar a opção quando não houver uma opção correta para a questão`() {

        val newOption = OptionDto(id = null, description = "New Option", isRight = true, questionId = validQuestionId)
        val existingOptions =
            listOf(OptionDto(id = 2, description = "Other Option", isRight = false, questionId = validQuestionId))

        every { questionRepository.findById(validQuestionId) } returns validQuestion
        every { optionRepository.findAllByQuestionId(validQuestionId) } returns existingOptions
        every { optionRepository.save(any<OptionDto>()) } returns newOption

        val savedOption = optionService.save(createOptionRequest)

        assertNotNull(savedOption)
        assertEquals("New Option", savedOption.description)
        assertTrue(savedOption.isRight)

        verify(exactly = 1) { questionRepository.findById(validQuestionId) }
        verify(exactly = 1) { optionRepository.save(any<OptionDto>()) }
    }
}
