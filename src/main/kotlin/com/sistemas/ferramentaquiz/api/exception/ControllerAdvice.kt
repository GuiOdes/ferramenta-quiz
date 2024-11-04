package com.sistemas.ferramentaquiz.api.exception

import com.guiodes.dizimum.domain.exception.BadRequestException
import com.guiodes.dizimum.domain.exception.ForbiddenException
import com.sistemas.ferramentaquiz.api.response.ErrorResponse
import com.sistemas.ferramentaquiz.api.response.FieldErrorResponse
import com.sistemas.ferramentaquiz.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(ex: NotFoundException): ErrorResponse {
        return ErrorResponse(
            message = ex.message,
            statusCode = 404
        )
    }

    @ExceptionHandler(ForbiddenException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleForbiddenExceptionException(ex: ForbiddenException): ErrorResponse {
        return ErrorResponse(
            message = ex.message,
            statusCode = 403
        )
    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestExceptionException(ex: BadRequestException): ErrorResponse {
        return ErrorResponse(
            message = ex.message,
            statusCode = 400
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): List<FieldErrorResponse> {
        return ex.bindingResult.fieldErrors.map {
            FieldErrorResponse(
                field = it.field,
                message = it.defaultMessage ?: "Invalid value"
            )
        }
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @Suppress("UnusedPrivateMember")
    fun handleUnexpectedException(ex: Exception): ErrorResponse {
        println(ex.message)
        return ErrorResponse(
            message = "An unexpected error occurred",
            statusCode = 500
        )
    }
}
