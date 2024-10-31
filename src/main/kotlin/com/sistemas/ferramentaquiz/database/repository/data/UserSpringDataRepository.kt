package com.sistemas.ferramentaquiz.database.repository.data

import com.sistemas.ferramentaquiz.database.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserSpringDataRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}
