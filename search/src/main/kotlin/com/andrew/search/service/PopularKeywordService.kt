package com.andrew.search.service

import com.andrew.search.domain.Keyword
import com.andrew.search.domain.PopularKeyword
import com.andrew.search.repository.KeywordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PopularKeywordService {
    fun incrementCount(keyword: String)
    fun getPopularKeywords(): List<PopularKeyword>?
}

@Service
class PopularKeywordServiceImpl(
    private val keywordRepository: KeywordRepository
) : PopularKeywordService {

    @Transactional
    override fun incrementCount(keyword: String) {
        val existingKeyword = keywordRepository.findByKeyword(keyword)
        if (existingKeyword != null) {
            existingKeyword.count = existingKeyword.count?.plus(1)
            keywordRepository.save(existingKeyword)
        } else {
            val newKeyword = Keyword()
            newKeyword.keyword = keyword
            newKeyword.count = 1
            keywordRepository.save(newKeyword)
        }
    }

    override fun getPopularKeywords(): List<PopularKeyword> {
        val keywords: List<Keyword> = keywordRepository.findTop10ByCountDesc()
        return keywords.map { convertToPopularKeyword(it) }
    }

    fun convertToPopularKeyword(keyword: Keyword): PopularKeyword {
        return PopularKeyword(keyword.keyword!!, keyword.count!!)
    }
}