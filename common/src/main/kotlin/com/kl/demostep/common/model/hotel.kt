package com.kl.demostep.common.model

import kotlinx.serialization.Serializable

@Serializable
data class BookHotelRequest(
    val orderId: String,
    val hotelSelectionId: String,
    val taskToken: String,
)

@Serializable
data class BookHotelResponse(
    val bookedHotelId: String,
)

@Serializable
data class CancelHotelRequest(
    val bookedHotelId: String,
    val taskToken: String,
)