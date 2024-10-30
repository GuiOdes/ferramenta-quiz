package com.sistemas.ferramenta_quiz.database.repository.data

import com.sistemas.ferramenta_quiz.database.entity.GuestEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GuestSpringDataRepository: JpaRepository<GuestEntity, Long>