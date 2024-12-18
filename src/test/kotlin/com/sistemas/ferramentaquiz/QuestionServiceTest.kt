package com.sistemas.ferramentaquiz

import com.sistemas.ferramentaquiz.api.controller.QuestionController
import com.sistemas.ferramentaquiz.api.request.CreateQuestionRequest

import com.sistemas.ferramentaquiz.database.repository.QuestionRepository
import com.sistemas.ferramentaquiz.database.repository.QuizRepository
import com.sistemas.ferramentaquiz.database.repository.UserRepository
import com.sistemas.ferramentaquiz.dto.QuestionDto
import com.sistemas.ferramentaquiz.dto.QuizDto
import com.sistemas.ferramentaquiz.dto.UserDto
import com.sistemas.ferramentaquiz.exception.NotFoundException
import com.sistemas.ferramentaquiz.service.JwtService
import com.sistemas.ferramentaquiz.service.QuestionService
import io.mockk.clearMocks
import io.mockk.every

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class QuestionServiceTest {
    private val service: QuestionService = mockk()
    private val jwtService: JwtService = mockk()


    private val controller = QuestionController(service)

    private val questionRepository: QuestionRepository = mockk()
    private val quizRepository: QuizRepository = mockk()
    private val questionService = QuestionService(questionRepository, quizRepository)


    private val userRepository: UserRepository = mockk()
    private val userDto = UserDto(
        id = 1,
        email = "test@example.com",
        name = "Test User",
        password = "password123"
    )

    private val validQuizId = 1L
    private val invalidQuizId = 999L
    private val validRequest = CreateQuestionRequest(
        title = "Question Title",
        description = "Question Description",
        quizId = validQuizId
    )

    private val validQuiz = QuizDto(
        id = validQuizId,
        title = "Valid Quiz",
        code = "ABC123",
        isDone = false,
        user = userDto,
        guests = emptyList(),
        questions = emptyList()
    )
    private val questionDto = QuestionDto(
        id = 1,
        title = "Sample Question",
        description = "Description of the question",
        quizId = validQuizId
    )

    private val createQuestionRequest = CreateQuestionRequest(
        title = "Sample Question",
        description = "Description of the question",
        quizId = validQuizId
    )

    @BeforeEach
    fun setup() {
        clearMocks(questionRepository, quizRepository)
    }

    @Test
    fun `deve salvar uma pergunta com sucesso`() {

        every { quizRepository.findById(validQuizId) } returns validQuiz
        every { questionRepository.save(any<QuestionDto>()) } returns questionDto

        val savedQuestion = questionService.save(createQuestionRequest)

        assertNotNull(savedQuestion)
        assertEquals("Sample Question", savedQuestion.title)
        assertEquals("Description of the question", savedQuestion.description)
        assertEquals(validQuizId, savedQuestion.quizId)

        verify(exactly = 1) { quizRepository.findById(validQuizId) }
        verify(exactly = 1) { questionRepository.save(any<QuestionDto>()) }
    }

    @Test
    fun `deve lançar NotFoundException quando o quiz não for encontrado`() {

        every { quizRepository.findById(validQuizId) } returns null

        val exception = assertThrows<NotFoundException> {
            questionService.save(createQuestionRequest)
        }

        assertEquals("QuizDto not found!", exception.message)

        verify(exactly = 1) { quizRepository.findById(validQuizId) }
        verify(exactly = 0) { questionRepository.save(any<QuestionDto>()) }
    }

    @Test
    fun `deve retornar todas as questões para um quiz`() {

        val questionDto = QuestionDto(title = "Question 1", description = "Description 1", quizId = validQuizId)
        val questionList = listOf(questionDto)

        every { service.findAllByQuizId(validQuizId) } returns questionList

        val result = controller.findAllByQuizId(validQuizId)

        assertEquals(1, result.size)
        assertEquals("Question 1", result[0].title)

        verify(exactly = 1) { service.findAllByQuizId(validQuizId) }
    }
}