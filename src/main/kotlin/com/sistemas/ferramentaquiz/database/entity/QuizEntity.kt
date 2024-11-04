package com.sistemas.ferramentaquiz.database.entity

import com.sistemas.ferramentaquiz.dto.QuizDto
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@Table(name = "quiz")
class QuizEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_seq")
    @SequenceGenerator(name = "quiz_seq", sequenceName = "quiz_seq", allocationSize = 1)
    val id: Long? = null,
    val title: String,
    val code: String,
    val isDone: Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity
) {
    fun toDto() = QuizDto(
        id = id,
        title = title,
        code = code,
        isDone = isDone,
        user = user
    )
}
