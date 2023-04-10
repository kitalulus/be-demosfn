package com.kl.demostep.common.model

import kotlinx.serialization.Serializable

@Serializable
data class HoldPaymentRequest(
    val orderId: String,
    val amount: Double,
    val taskToken: String,
)

@Serializable
data class HoldPaymentResponse(
    val paymentId: String,
)

@Serializable
data class ReleaseHoldRequest(
    val paymentId: String,
    val taskToken: String,
)

@Serializable
data class SettlePaymentRequest(
    val paymentId: String,
    val taskToken: String,
)

@Serializable
data class SendInvoiceRequest(
    val paymentId: String,
    val taskToken: String,
)
