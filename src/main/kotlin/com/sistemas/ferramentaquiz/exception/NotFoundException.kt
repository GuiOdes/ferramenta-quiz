package com.sistemas.ferramentaquiz.exception

import com.guiodes.dizimum.domain.exception.BaseRuntimeException
import kotlin.reflect.KClass

class NotFoundException(
    val classNotFound: KClass<*>,
    override val message: String = "${classNotFound.simpleName} not found!",
    override val statusCode: Int = 404
) : BaseRuntimeException(message, statusCode)
