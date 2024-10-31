package com.sistemas.ferramentaquiz.database.repository.data

import com.sistemas.ferramentaquiz.database.entity.QuizEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizSpringDataRepository: JpaRepository<QuizEntity, Long> {
}