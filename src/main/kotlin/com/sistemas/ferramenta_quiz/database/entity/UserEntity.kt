package com.sistemas.ferramenta_quiz.database.entity

import com.sistemas.ferramenta_quiz.dto.UserDto
import jakarta.persistence.*
import org.springframework.data.jpa.domain.AbstractPersistable_.id

@Entity
@Table(name = "user")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
    val id: Long? = null,
    val name: String,
    val email: String,
    val password: String
){
    fun toDto() = UserDto(
        id = id,
        name = name,
        email = email,
        password = password
    )
}