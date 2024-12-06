package com.sistemas.ferramentaquiz.api.request

import jakarta.validation.constraints.NotEmpty

data class SumHitOptionRequest(
    @field:NotEmpty(message = "ids is required")
    val ids: List<Long>
)
