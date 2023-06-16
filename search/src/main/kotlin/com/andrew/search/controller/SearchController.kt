package com.andrew.search.controller

import com.andrew.search.domain.Document
import com.andrew.search.domain.PopularKeyword
import com.andrew.search.service.SearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class SearchController {
    @Autowired
    private lateinit var searchService: SearchService

    @GetMapping("/search")
    fun searchBlogs(
        @RequestParam("query", required = true) keyword: String,
        @RequestParam("sort", defaultValue = "accuracy") sort: String,
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int
    ): List<Document>? {
        return searchService.searchBlogs(keyword, sort, page, size)
    }

    @GetMapping("/popular")
    fun getPopularKeywords(): List<PopularKeyword>? {
        return searchService.getPopularKeywords()
    }
}