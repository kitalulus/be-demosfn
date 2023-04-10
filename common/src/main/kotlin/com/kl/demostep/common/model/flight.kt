package com.kl.demostep.common.model

import kotlinx.serialization.Serializable

@Serializable
data class BookFlightRequest(
    val orderId: String,
    val flightSelectionId: String,
    val taskToken: String,
)

@Serializable
data class BookFlightResponse(
    val bookedFlightId: String,
)

@Serializable
data class CancelFlightRequest(
    val bookedFlightId: String,
    val taskToken: String,
)

@Serializable
data class SendPlaneTicketRequest(
    val orderId: String,
    val bookedFlightId: String,
    val taskToken: String,
)
