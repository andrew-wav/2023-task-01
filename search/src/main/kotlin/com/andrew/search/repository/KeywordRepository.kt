package com.andrew.search.repository

import com.andrew.search.entity.Keyword
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface KeywordRepository : JpaRepository<Keyword, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByKeyword(keyword: String): Keyword?

    @Query("SELECT k FROM Keyword k ORDER BY k.count DESC LIMIT 10")
    fun findTop10ByCountDesc(): List<Keyword>?
}