package com.kl.demostep.common.model

import kotlinx.serialization.Serializable

@Serializable
data class SfnOrderRequest (
    val orderId: String,
    val flightSelectionId: String,
    val hotelSelectionId: String,
    val amount: Double,
)