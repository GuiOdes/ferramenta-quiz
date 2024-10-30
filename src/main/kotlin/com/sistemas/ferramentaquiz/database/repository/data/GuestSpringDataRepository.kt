package com.sistemas.ferramentaquiz.database.repository.data

import com.sistemas.ferramentaquiz.database.entity.GuestEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GuestSpringDataRepository : JpaRepository<GuestEntity, Long>
