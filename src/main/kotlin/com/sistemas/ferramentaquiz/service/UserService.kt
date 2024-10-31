package com.sistemas.ferramentaquiz.service

import com.guiodes.dizimum.domain.exception.ForbiddenException
import com.guiodes.dizimum.domain.exception.NotFoundException
import com.sistemas.ferramentaquiz.api.request.AuthenticationRequest
import com.sistemas.ferramentaquiz.api.request.CreateUserRequest
import com.sistemas.ferramentaquiz.api.response.AuthenticationResponse
import com.sistemas.ferramentaquiz.database.repository.UserRepository
import com.sistemas.ferramentaquiz.dto.UserDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {
    fun save(request: CreateUserRequest) {
        val encodedPasswordUser = request.copy(
            password = passwordEncoder.encode(request.password)
        ).toDto()

        repository.save(encodedPasswordUser)
    }

    fun findAll() = repository.findAll()

    fun generateJwtToken(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        val user = userRepository.findByEmail(authenticationRequest.username)
            ?: throw NotFoundException(UserDto::class)

        if (!passwordEncoder.matches(authenticationRequest.password, user.password)) {
            throw ForbiddenException("Invalid password!")
        }

        return jwtService.generateToken(user)
    }
}
