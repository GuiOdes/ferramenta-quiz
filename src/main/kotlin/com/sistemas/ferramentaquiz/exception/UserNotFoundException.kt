package com.sistemas.ferramentaquiz.exception

class UserNotFoundException(
    override val message: String = "User not found",
) : RuntimeException(message)