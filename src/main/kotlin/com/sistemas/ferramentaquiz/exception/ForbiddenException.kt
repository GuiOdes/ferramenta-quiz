package com.guiodes.dizimum.domain.exception

class ForbiddenException(
    override val message: String = "Forbidden request!",
    override val statusCode: Int = 403
) : BaseRuntimeException(message, statusCode)
