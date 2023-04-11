package com.kl.demostep.hotel.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "application.hotel")
data class HotelAppConfig(
    val queueBookHotel: String,
    val queueCancelBook: String,
    val queueSendHotelConfirmation: String,
)
