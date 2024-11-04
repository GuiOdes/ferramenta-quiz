package com.sistemas.ferramentaquiz.database.repository.data

import com.sistemas.ferramentaquiz.database.entity.QuizEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuizSpringDataRepository : JpaRepository<QuizEntity, Long> {

    fun findAllByUserEmail(email: String): List<QuizEntity>
    @Query("SELECT q FROM QuizEntity q WHERE q.code = :code AND q.isDone = false")
    fun findActiveByCode(code: String): QuizEntity?
}
