package com.kl.demostep.order.controller

import com.amazonaws.services.stepfunctions.AWSStepFunctions
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest
import com.kl.demostep.common.model.SfnOrderRequest
import com.kl.demostep.order.config.OrderAppConfig
import com.kl.demostep.order.model.TravelOrderRequest
import com.kl.demostep.order.model.TravelOrderResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/order")
class OrderController(
    val sfnClient: AWSStepFunctions,
    val orderAppConfig: OrderAppConfig,
) {
    @PostMapping
    fun createOrder(@RequestBody request: TravelOrderRequest): TravelOrderResponse {
        // run a state machine
        val name = "order-${request.orderId}"

        val sfnOrderRequest = SfnOrderRequest(
            request.orderId,
            request.flightSelectionId,
            request.hotelSelectionId,
            100.0,
        )

        val theReq = StartExecutionRequest()
            .withName(name)
            .withStateMachineArn(orderAppConfig.sfnNewOrderArn)
            .withInput(Json.encodeToString(sfnOrderRequest))
        val response = sfnClient.startExecution(theReq)

        return TravelOrderResponse(true, "Order created", theReq.name)
    }

    @GetMapping(produces = ["text/plain"])
    fun getOrder(): String = "Order Service"
}