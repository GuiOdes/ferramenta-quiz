package com.sistemas.ferramentaquiz.database.entity

import com.sistemas.ferramentaquiz.dto.UserDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "tb_user")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
    val id: Long? = null,
    val name: String,
    val email: String,
    @Column(name = "password")
    val userPassword: String
) : UserDetails {
    fun toDto() = UserDto(
        id = id,
        name = name,
        email = email,
        password = userPassword
    )

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword() = userPassword

    override fun getUsername() = email
}
