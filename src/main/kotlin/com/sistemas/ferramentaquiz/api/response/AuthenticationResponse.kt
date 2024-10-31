package com.sistemas.ferramentaquiz.api.response

data class AuthenticationResponse(
    val authToken: String,
    val refreshToken: String
)
