package com.andrew.search.domain

data class SearchResponse(
    val code : Int,
    val status : String,
    val error: String? = null,
    val documents : List<Document>? = null,
)

data class KeywordsResponse(
    val code : Int,
    val status : String,
    val error: String? = null,
    val keywords : List<PopularKeyword>? = null,
)
