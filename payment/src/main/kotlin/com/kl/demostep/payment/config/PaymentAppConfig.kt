package com.kl.demostep.payment.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "application.payment")
data class PaymentAppConfig (
    val queueHoldPayment: String,
    val queueSettlePayment: String,
    val queueCancelPayment: String,
    val queueSendInvoice: String,
)
