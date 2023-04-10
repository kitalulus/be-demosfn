package com.kl.demostep.flight

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "com.kl.demostep.common.config",
        "com.kl.demostep.flight",
    ]
)
@ConfigurationPropertiesScan
class FlightApplication

fun main(args: Array<String>) {
    runApplication<FlightApplication>(*args)
}
