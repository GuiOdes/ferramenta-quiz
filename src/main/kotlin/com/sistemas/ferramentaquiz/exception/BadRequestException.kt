package com.guiodes.dizimum.domain.exception

class BadRequestException(
    override val message: String = "Bad request!",
    override val statusCode: Int = 400
) : BaseRuntimeException(message, statusCode)
