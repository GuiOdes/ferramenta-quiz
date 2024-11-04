package com.sistemas.ferramentaquiz.database.repository.data

import com.sistemas.ferramentaquiz.database.entity.QuestionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionSpringDataRepository : JpaRepository<QuestionEntity, Long> {

    fun findAllByQuizId(quizId: Long): List<QuestionEntity>
}
