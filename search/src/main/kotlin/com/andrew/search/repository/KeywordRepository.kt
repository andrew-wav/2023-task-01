package com.andrew.search.repository

import com.andrew.search.entity.Keyword
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface KeywordRepository : JpaRepository<Keyword, Long> {
    fun findByKeyword(keyword: String): Keyword?

    @Query("SELECT k FROM Keyword k ORDER BY k.count DESC LIMIT 10")
    fun findTop10ByCountDesc(): List<Keyword>?
}