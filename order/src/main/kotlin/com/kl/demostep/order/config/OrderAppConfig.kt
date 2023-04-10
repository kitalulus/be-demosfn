package com.kl.demostep.order.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "application.order")
data class OrderAppConfig (
    val sfnNewOrderArn: String,
)