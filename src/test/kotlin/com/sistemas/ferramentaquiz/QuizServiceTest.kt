import com.sistemas.ferramentaquiz.api.controller.QuizController
import com.sistemas.ferramentaquiz.api.request.CreateQuizRequest
import com.sistemas.ferramentaquiz.database.repository.QuizRepository
import com.sistemas.ferramentaquiz.database.repository.UserRepository

import com.sistemas.ferramentaquiz.dto.QuizDto
import com.sistemas.ferramentaquiz.dto.UserDto
import com.sistemas.ferramentaquiz.service.GuestService
import com.sistemas.ferramentaquiz.service.JwtService
import com.sistemas.ferramentaquiz.service.QuizService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class QuizServiceTest {

    private val service: QuizService = mockk()
    private val jwtService: JwtService = mockk()

    // Instância da classe a ser testada
    private val controller = QuizController(service, jwtService, mockk())

    private val userRepository: UserRepository = mockk()
    private val userDto = UserDto(
        id = 1,
        email = "test@example.com",
        name = "Test User",
        password = "password123"
    )

    @Test
    fun `deve criar um quiz com sucesso`() {
        // Arrange: Configuração inicial
        val token = "Bearer validToken"
        val userEmail = userDto.email
        val request = CreateQuizRequest(title = "Quiz Test")
        val generatedCode = "ABC123"

        // Mock do comportamento esperado
        every { jwtService.extractUsername(token) } returns userEmail
        every { service.generateAccessCode() } returns generatedCode
        every { service.save(request, userEmail) } just runs

        // Act: Executa o método do controller
        controller.save(request, token)

        // Assert: Verifica se os métodos foram chamados corretamente
        verify(exactly = 1) { jwtService.extractUsername(token) }
        verify(exactly = 1) { service.save(request, userEmail) }
    }
    @Test
    fun `deve lançar exceção quando o token for inválido`() {
        // Arrange: Configuração inicial
        val token = "Bearer invalidToken"
        val request = CreateQuizRequest(title = "Quiz Test")

        // Mock: Extrair username retorna erro
        every { jwtService.extractUsername(token) } throws IllegalArgumentException("Invalid token")

        // Act & Assert: Deve lançar a exceção esperada
        val exception = assertThrows<IllegalArgumentException> {
            controller.save(request, token)
        }

        // Verifica se a mensagem de erro está correta
        assertEquals("Invalid token", exception.message)

        // Assert: Verifica se jwtService foi chamado apenas uma vez
        verify(exactly = 1) { jwtService.extractUsername(token) }

        // Não deve chamar o service.save
        verify(exactly = 0) { service.save(any(), any()) }
    }
    @Test
    fun `deve lançar NotFoundException se o usuário não existir`() {
        val token = "Bearer validToken"
        val userEmail = "nonexistent@example.com"
        val request = CreateQuizRequest(title = "Quiz Test")

        // Mock: Token é válido, mas o usuário não é encontrado
        every { jwtService.extractUsername(token) } returns userEmail
        every { service.save(request, userEmail) } throws com.sistemas.ferramentaquiz.exception.NotFoundException(UserDto::class)

        // Act & Assert: Deve lançar NotFoundException
        val exception = assertThrows<com.sistemas.ferramentaquiz.exception.NotFoundException> {
            controller.save(request, token)
        }

        // Verifique a mensagem de erro, com o ponto de exclamação!
        assertEquals("UserDto not found!", exception.message)

        // Verifica se jwtService e o save foram chamados corretamente
        verify(exactly = 1) { jwtService.extractUsername(token) }
        verify(exactly = 1) { service.save(request, userEmail) }
    }

}
