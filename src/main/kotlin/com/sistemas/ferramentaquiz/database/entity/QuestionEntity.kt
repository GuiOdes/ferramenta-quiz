package com.sistemas.ferramentaquiz.database.entity

import com.sistemas.ferramentaquiz.dto.QuestionDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@Table(name = "question")
class QuestionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    @SequenceGenerator(name = "question_seq", sequenceName = "question_seq", allocationSize = 1)
    val id: Long? = null,
    val title: String,
    val description: String,
    @ManyToOne
    val quiz: QuizEntity
) {

    fun toDto() = QuestionDto(
        id = id,
        title = title,
        description = description,
        quizId = quiz.id!!
    )
}
