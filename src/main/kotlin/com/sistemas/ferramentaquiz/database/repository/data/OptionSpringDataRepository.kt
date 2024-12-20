package com.sistemas.ferramentaquiz.database.repository.data

import com.sistemas.ferramentaquiz.database.entity.OptionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OptionSpringDataRepository : JpaRepository<OptionEntity, Long> {

    fun findAllByQuestionId(questionId: Long): List<OptionEntity>
}
