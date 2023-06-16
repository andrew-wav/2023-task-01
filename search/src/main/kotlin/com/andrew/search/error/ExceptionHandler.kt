package com.andrew.search.error

import org.springframework.http.HttpStatus

import org.springframework.web.bind.annotation.ExceptionHandler

import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException


@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(SearchParamException::class)
    fun handleParamException(e: SearchParamException?): ErrorResponse {
        return ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),
            status = HttpStatus.BAD_REQUEST.name,
            error = e?.message
        )
    }

    @ExceptionHandler(NotFoundKeywordException::class)
    fun handleParamException(e: NotFoundKeywordException?): ErrorResponse {
        return ErrorResponse(
            code = HttpStatus.NO_CONTENT.value(),
            status = HttpStatus.NO_CONTENT.name,
            error = e?.message
        )
    }

    @ExceptionHandler(HttpClientErrorException::class)
    fun handleClientException(e: HttpClientErrorException?): ErrorResponse {
        return ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),
            status = HttpStatus.BAD_REQUEST.name,
            error = e?.message
        )
    }

    @ExceptionHandler(HttpServerErrorException::class)
    fun handleServerException(e: HttpServerErrorException?): ErrorResponse {
        return ErrorResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            status = HttpStatus.INTERNAL_SERVER_ERROR.name,
            error = e?.message
        )
    }

    @ExceptionHandler(Error::class)
    fun handleException(e: Exception?): ErrorResponse {
        return ErrorResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            status = HttpStatus.INTERNAL_SERVER_ERROR.name,
            error = e?.message
        )
    }
}