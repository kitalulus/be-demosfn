package com.kl.demostep.order

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "com.kl.demostep.common.config",
        "com.kl.demostep.order",
    ]
)
@ConfigurationPropertiesScan
class OrderApplication

fun main(args: Array<String>) {
    runApplication<OrderApplication>(*args)
}
