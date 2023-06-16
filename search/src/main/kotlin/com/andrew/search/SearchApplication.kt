package com.andrew.search

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class SearchApplication

fun main(args: Array<String>) {
	runApplication<SearchApplication>(*args)
}
