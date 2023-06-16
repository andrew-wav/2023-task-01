package com.andrew.search.service

import com.andrew.search.domain.Document
import com.andrew.search.domain.PopularKeyword
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface SearchService {
    fun searchBlogs(keyword: String, sort: String, page: Int, size: Int): List<Document>?
    fun getPopularKeywords(): List<PopularKeyword>?
}

@Service
class SearchServiceImpl : SearchService {

    @Autowired
    private lateinit var blogSearchApi: BlogSearchApi

    @Autowired
    private lateinit var popularKeywordService: PopularKeywordService

    override fun searchBlogs(keyword: String, sort: String, page: Int, size: Int): List<Document>? {
        val results = blogSearchApi.searchKakaoBlogs(keyword, sort, page, size)
        popularKeywordService.incrementCount(keyword)
        return results.body?.documents
    }

    override fun getPopularKeywords(): List<PopularKeyword>? {
        return popularKeywordService.getPopularKeywords()
    }
}