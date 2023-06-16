package com.andrew.search.controller

import com.andrew.search.domain.KeywordsResponse
import com.andrew.search.domain.PopularKeyword
import com.andrew.search.domain.SearchResponse
import com.andrew.search.error.NotFoundKeywordException
import com.andrew.search.error.SearchParamException
import com.andrew.search.service.SearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1")
class SearchController {
    @Autowired
    private lateinit var searchService: SearchService

    @GetMapping("/search")
    fun searchBlogs(
        @RequestParam("query", required = false) keyword: String?,
        @RequestParam("sort", defaultValue = "accuracy") sort: String,
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int
    ): SearchResponse {
        if(keyword == "" || keyword == null)
            throw SearchParamException("query is requred")
        return SearchResponse(
            documents = searchService.searchBlogs(keyword, sort, page, size),
            status = HttpStatus.OK.name,
            code = HttpStatus.OK.value()
        )
    }

    @GetMapping("/popular")
    fun getPopularKeywords(): KeywordsResponse {
        val results = searchService.getPopularKeywords()
        if (results?.size == 0)
            throw NotFoundKeywordException("not found any keywords")
        return KeywordsResponse(
            keywords = results,
            status = HttpStatus.OK.name,
            code = HttpStatus.OK.value()
        )
    }
}