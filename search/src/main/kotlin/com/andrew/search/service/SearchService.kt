package com.andrew.search.service

import com.andrew.search.domain.Document
import com.andrew.search.domain.KakaoBlogResponse
import com.andrew.search.domain.PopularKeyword
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

interface SearchService {
    fun searchBlogs(keyword: String, sort: String, page: Int, size: Int): List<Document>?
    fun incrementCount(keyword: String)
    fun getPopularKeywords(): List<PopularKeyword>?
}

@Service
class SearchServiceImpl : SearchService {

    @Autowired
    private lateinit var blogSearchApi: BlogSearchApi

    @Autowired
    private lateinit var popularKeywordService: PopularKeywordService

    @Retryable(
        value = [Exception::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 1000, multiplier = 2.0)
    )
    override fun searchBlogs(keyword: String, sort: String, page: Int, size: Int): List<Document>? {
        // TODO: kakao 검색이 실패하면 naver 검색의 결과를 공동 Document로 변환 필요
        // val results = searchNaverBlogs(keyword, sort, page, size)
        // return results

        return blogSearchApi.searchKakaoBlogs(keyword, sort, page, size).body?.documents
    }

    @Retryable(
        value = [Exception::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 1000, multiplier = 2.0)
    )
    override fun incrementCount(keyword: String) {
        popularKeywordService.incrementCount(keyword)
    }

    override fun getPopularKeywords(): List<PopularKeyword>? {
        return popularKeywordService.getPopularKeywords()
    }
}