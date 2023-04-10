package com.kl.demostep.order.model

import kotlinx.serialization.Serializable

@Serializable
data class TravelOrderRequest (
    val orderId: String,
    val flightSelectionId: String,
    val hotelSelectionId: String,
)

@Serializable
data class TravelOrderResponse (
    val success: Boolean,
    val message: String,
    val requestName: String,
)