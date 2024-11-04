package com.sistemas.ferramentaquiz.exception

import com.sistemas.ferramentaquiz.api.response.ErrorResponse
import com.sistemas.ferramentaquiz.api.response.FieldErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserNotFoundException(exception: NotFoundException): ErrorResponse = ErrorResponse(
        message = exception.message,
        statusCode = HttpStatus.NOT_FOUND.value()
    )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(
        exception: MethodArgumentNotValidException
    ): List<FieldErrorResponse> {
        val fieldErrors = exception.bindingResult.fieldErrors.map {
            FieldErrorResponse(it.field, it.defaultMessage ?: "Invalid value")
        }

        return fieldErrors
    }
}
