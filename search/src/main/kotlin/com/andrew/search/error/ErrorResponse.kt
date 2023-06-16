package com.andrew.search.error

data class ErrorResponse(
    val code : Int,
    val status : String,
    val error: String? = null
)
