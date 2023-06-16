package com.andrew.search.entity

import jakarta.persistence.*

@Entity
@Table(name = "keywords")
class Keyword {
    @Id
    @Column(unique = true)
    var keyword: String? = null

    var count: Long? = 0
}