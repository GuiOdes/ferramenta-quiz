package com.sistemas.ferramentaquiz.database.entity

import com.sistemas.ferramentaquiz.dto.OptionDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@Table(name = "option")
data class OptionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "option_seq")
    @SequenceGenerator(name = "option_seq", sequenceName = "option_seq", allocationSize = 1)
    val id: Long? = null,
    val description: String,
    val isRight: Boolean,
    @ManyToOne
    val question: QuestionEntity,
    var hitCount: Long = 0
) {

    fun toDto() = OptionDto(
        id = id,
        description = description,
        isRight = isRight,
        questionId = question.id!!,
        hitCount = hitCount
    )
}
