package com.andrew.search.service

import com.andrew.search.domain.KakaoBlogResponse
import com.andrew.search.domain.NaverBlogResponse
import com.andrew.search.domain.PopularKeyword
import org.hibernate.internal.util.collections.CollectionHelper.listOf
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class BlogSearchApi {
    @Value("\${config.api.kakaokey}")
    private lateinit var kakaoApiKey: String

    fun searchKakaoBlogs(keyword: String, sort: String, page: Int, size: Int): ResponseEntity<KakaoBlogResponse> {
        val apiUrl = "https://dapi.kakao.com/v2/search/blog"
        val uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
            .queryParam("query", keyword)
            .queryParam("sort", sort)
            .queryParam("page", page)
            .queryParam("size", size)
            .build()
            .toUri()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set("Authorization", "KakaoAK $kakaoApiKey")

        val requestEntity = org.springframework.http.RequestEntity<Any>(headers, HttpMethod.GET, uri)
        val restTemplate = RestTemplate()
        return restTemplate.exchange(requestEntity, KakaoBlogResponse::class.java)
    }

    fun searchNaverBlogs(keyword: String, sort: String, page: Int, size: Int): ResponseEntity<NaverBlogResponse> {

        val sorting : String = if (sort == "accuracy") "sim" else "date"
        val apiUrl = "https://openapi.naver.com/v1/search/blog.json"
        val uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
            .queryParam("query", keyword)
            .queryParam("sort", sorting)
            .queryParam("start", page * size)
            .queryParam("display", size)
            .build()
            .toUri()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        // TODO: naver API 클라이언트키 등록 필요
        headers.set("X-Naver-Client-Id", "client id")
        headers.set("X-Naver-Client-Secret", "client secret")

        val requestEntity = org.springframework.http.RequestEntity<Any>(headers, HttpMethod.GET, uri)
        val restTemplate = RestTemplate()
        return restTemplate.exchange(requestEntity, NaverBlogResponse::class.java)
    }

}