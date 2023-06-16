package com.andrew.search.domain

data class KakaoBlogResponse(
    val meta: Meta,
    val documents : List<Document>,
)

data class Document (
    val title: String,
    val contents: String,
    val url: String,
    val blogname: String,
    val thumbnail: String,
    val datetime: String,
)

data class Meta (
    val total_count: Int,
    val pageable_count: Int,
    val is_end: Boolean,
)