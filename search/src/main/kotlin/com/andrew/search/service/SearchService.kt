package com.andrew.search.service

import com.andrew.search.domain.Document
import com.andrew.search.domain.KakaoBlogResponse
import com.andrew.search.domain.PopularKeyword
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException

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
        try {
            val results = blogSearchApi.searchKakaoBlogs(keyword, sort, page, size)
            popularKeywordService.incrementCount(keyword)
            return results.body?.documents
        } catch (e: Exception) {
            // TODO: kakao 검색이 실패하면 naver 검색의 결과를 가져오도록
            // val results = blogSearchApi.searchNaverBlogs(keyword, sort, page, size)
            // popularKeywordService.incrementCount(keyword)
            // return results.body?.documents
            throw InternalError("Kakao blog search is not available")
        }
    }

    override fun getPopularKeywords(): List<PopularKeyword>? {
        return popularKeywordService.getPopularKeywords()
    }
}