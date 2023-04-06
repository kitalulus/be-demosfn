package com.kl.demostep.order.controller

import com.amazonaws.services.stepfunctions.AWSStepFunctions
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest
import com.kl.demostep.order.model.TravelOrderRequest
import com.kl.demostep.order.model.TravelOrderResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order")
class OrderController(
    val sfnClient: AWSStepFunctions,
) {
    @PostMapping
    fun createOrder(@RequestBody request: TravelOrderRequest): TravelOrderResponse {
        // run a state machine
        val theReq = StartExecutionRequest()
            .withStateMachineArn("arn:aws:states:ap-southeast-1:846147588383:stateMachine:Test123")
            .withInput("{\"name\": \"World\"}")
        val response = sfnClient.startExecution(theReq)

        return TravelOrderResponse(true, "Order created")
    }

    @GetMapping
    fun getOrder(): String = "hello there yowdy"
}