package com.task.bsearch.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.yaml.snakeyaml.constructor.BaseConstructor

@RestController
@RequestMapping("api")
class SearchController {
    @GetMapping("/test")
    fun test() : String {
        return "hello"
    }
}