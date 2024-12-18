import com.guiodes.dizimum.domain.exception.ForbiddenException
import com.sistemas.ferramentaquiz.api.request.AuthenticationRequest
import com.sistemas.ferramentaquiz.api.request.CreateUserRequest
import com.sistemas.ferramentaquiz.api.response.AuthenticationResponse
import com.sistemas.ferramentaquiz.database.repository.UserRepository
import com.sistemas.ferramentaquiz.database.repository.data.UserSpringDataRepository
import com.sistemas.ferramentaquiz.dto.UserDto
import com.sistemas.ferramentaquiz.service.JwtService
import com.sistemas.ferramentaquiz.service.UserService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.apache.coyote.BadRequestException
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.verify

class UserServiceTest {
    private val userRepository: UserRepository = mockk()
    private val passwordEncoder: PasswordEncoder = mockk()
    private val jwtService: JwtService = mockk()
    private val repository: UserRepository = mockk()
    private val userService = UserService(userRepository, passwordEncoder, userRepository, jwtService)
    private val userSpringDataRepository: UserSpringDataRepository = mockk()

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `deve gerar token JWT e token de atualização quando as credenciais forem válidas`() {

        val authenticationRequest = AuthenticationRequest("test@example.com", "password123")
        val user = UserDto(email = "test@example.com", password = "encodedPassword", name = "test")
        val expectedResponse = AuthenticationResponse(
            authToken = "valid.auth.token.jwt",
            refreshToken = "valid.refresh.token.jwt"
        )

        every { userRepository.findByEmail(authenticationRequest.username) } returns user
        every { passwordEncoder.matches(authenticationRequest.password, user.password) } returns true
        every { jwtService.generateToken(user) } returns expectedResponse

        val response = userService.generateJwtToken(authenticationRequest)

        assertNotNull(response)
        assertEquals("valid.auth.token.jwt", response.authToken)
        assertEquals("valid.refresh.token.jwt", response.refreshToken)

        verify(exactly = 1) { userRepository.findByEmail(authenticationRequest.username) }
        verify(exactly = 1) { passwordEncoder.matches(authenticationRequest.password, user.password) }
        verify(exactly = 1) { jwtService.generateToken(user) }
    }

    @Test
    fun `deve lançar ForbiddenException quando a senha for inválida`() {
        val authenticationRequest = AuthenticationRequest("test@example.com", "wrongPassword")
        val user = UserDto(email = "test@example.com", password = "encodedPassword", name = "test")

        every { userRepository.findByEmail(authenticationRequest.username) } returns user
        every { passwordEncoder.matches(authenticationRequest.password, user.password) } returns false


        val exception = assertThrows<ForbiddenException> {
            userService.generateJwtToken(authenticationRequest)
        }
        assertEquals("Invalid password!", exception.message)


        verify(exactly = 1) { userRepository.findByEmail(authenticationRequest.username) }
        verify(exactly = 1) { passwordEncoder.matches(authenticationRequest.password, user.password) }
        verify(exactly = 0) { jwtService.generateToken(any()) } // Não deve ser chamado
    }

    @Test
    fun `deve salvar novo usuario quando email nao existe`() {

        val request = CreateUserRequest(
            name = "Test User",
            email = "test@example.com",
            password = "password123"
        )

        val encodedPassword = "encodedPassword123"

        // Simula o UserDto que será salvo
        val userDtoToSave = UserDto(
            name = request.name,
            email = request.email,
            password = encodedPassword
        )

        val userEntityToSave = userDtoToSave.toEntity()

        every { userRepository.findByEmail(request.email) } returns null

        every { passwordEncoder.encode(request.password) } returns encodedPassword

        every { userRepository.save(any()) } returns userEntityToSave.toDto()

        userService.save(request)

        verify(exactly = 1) { userRepository.findByEmail(request.email) }
        verify(exactly = 1) { passwordEncoder.encode(request.password) }
        verify(exactly = 1) {
            userRepository.save(withArg {
                assertEquals(userEntityToSave.email, it.email)
                assertEquals(userEntityToSave.password, it.password)
            })
        }
    }
}