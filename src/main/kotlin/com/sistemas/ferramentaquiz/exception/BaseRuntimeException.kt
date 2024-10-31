package com.guiodes.dizimum.domain.exception

import java.lang.RuntimeException

open class BaseRuntimeException(
    override val message: String = "Runtime Error!",
    open val statusCode: Int = 500
) : RuntimeException(message)
