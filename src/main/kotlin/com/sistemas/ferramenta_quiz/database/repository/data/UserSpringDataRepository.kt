package com.sistemas.ferramenta_quiz.database.repository.data

import com.sistemas.ferramenta_quiz.database.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserSpringDataRepository: JpaRepository<UserEntity, Long>