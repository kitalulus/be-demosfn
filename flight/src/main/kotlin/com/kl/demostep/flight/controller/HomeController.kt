package com.kl.demostep.flight.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {
    @GetMapping(produces = ["text/plain"])
    fun home() = "Flight Service"
}