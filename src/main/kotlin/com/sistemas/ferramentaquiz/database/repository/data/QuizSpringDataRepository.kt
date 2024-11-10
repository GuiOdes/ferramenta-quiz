package com.sistemas.ferramentaquiz.database.repository.data

import com.sistemas.ferramentaquiz.database.entity.QuizEntity
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuizSpringDataRepository : JpaRepository<QuizEntity, Long> {

    @EntityGraph(value = "QuizEntity.graph", type = EntityGraph.EntityGraphType.FETCH)
    fun findAllByUserEmail(email: String): List<QuizEntity>
    @Query("SELECT q FROM QuizEntity q WHERE q.code = :code AND q.isDone = false")
    fun findActiveByCode(code: String): QuizEntity?

    fun findByQuestionsId(questionId: Long): QuizEntity?
}
