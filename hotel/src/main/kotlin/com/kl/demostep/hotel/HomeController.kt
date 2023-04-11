package com.kl.demostep.hotel

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {
    @GetMapping(produces = ["text/plain"])
    fun home() = "Hotel Service"
}