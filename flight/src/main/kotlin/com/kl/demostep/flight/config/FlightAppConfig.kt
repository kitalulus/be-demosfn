package com.kl.demostep.flight.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "application.flight")
data class FlightAppConfig(
    val queueBookFlight: String,
    val queueCancelFlight: String,
    val queueSendTicket: String,
)
