package com.sistemas.ferramentaquiz.database.entity

import com.sistemas.ferramentaquiz.dto.GuestDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity(name = "guest")
@Table(name = "guest")
class GuestEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guest_seq")
    @SequenceGenerator(name = "guest_seq", sequenceName = "guest_seq", allocationSize = 1)
    val id: Long? = null,
    val name: String,
    val ip: String,
    var score: Int = 0
) {

    fun toDto() = GuestDto(
        id = id,
        name = name,
        ip = ip,
        score = score
    )
}
