package com.sistemas.ferramentaquiz.database.repository.data

import com.sistemas.ferramentaquiz.database.entity.OptionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OptionSpringDataRepository : JpaRepository<OptionEntity, Long> {

    fun findAllByQuestionId(questionId: Long): List<OptionEntity>

    @Query("update OptionEntity o set o.hitCount = o.hitCount + 1 where o.id in :ids")
    fun sumCountByIds(ids: List<Long>)
}
