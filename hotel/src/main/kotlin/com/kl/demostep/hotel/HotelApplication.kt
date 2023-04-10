package com.kl.demostep.hotel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "com.kl.demostep.common.config",
        "com.kl.demostep.hotel",
    ]
)
@ConfigurationPropertiesScan
class HotelApplication

fun main(args: Array<String>) {
    runApplication<HotelApplication>(*args)
}
