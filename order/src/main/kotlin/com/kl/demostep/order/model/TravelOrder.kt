package com.kl.demostep.order.model

data class TravelOrderRequest (
    val name: String,
    val dateFrom: String,
    val dateTo: String,
    val bookingFlightId: String,
    val bookingHotelId: String,
)

data class TravelOrderResponse (
    val success: Boolean,
    val message: String,
)