package com.kl.demostep.flight.queue

import com.amazonaws.services.stepfunctions.AWSStepFunctions
import com.amazonaws.services.stepfunctions.model.SendTaskFailureRequest
import com.amazonaws.services.stepfunctions.model.SendTaskSuccessRequest
import com.kl.demostep.common.model.BookFlightRequest
import com.kl.demostep.common.model.BookFlightResponse
import com.kl.demostep.common.model.CancelFlightRequest
import com.kl.demostep.common.model.SendPlaneTicketRequest
import com.kl.demostep.common.utils.logger
import com.kl.demostep.common.utils.parseJsonFromStringEither
import com.kl.demostep.common.utils.toJsonString
import com.kl.demostep.flight.config.FlightAppConfig
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy
import io.awspring.cloud.messaging.listener.annotation.SqsListener
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Service

@Service
class FlightQueue(
    val flightAppConfig: FlightAppConfig,
    val sfnClient: AWSStepFunctions,
) {
    val log = logger()

    private val bookFlightSuccess = true
    private val cancelFlightSuccess = true
    private val sendTicketSuccess = true

    @SqsListener(
        value = ["#{flightQueue.flightAppConfig.queueBookFlight}"],
        deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS,
    )
    fun bookFlight(msg: String) {
        val request = parseJsonFromStringEither<BookFlightRequest>(msg)
        log.info("bookFlight: $request")

        if (bookFlightSuccess) {
            val successRequestOutput = BookFlightResponse(
                bookedFlightId = "FLIGHT-${request.orderId}",
            )
            val sendTaskRequest = SendTaskSuccessRequest()
                .withTaskToken(request.taskToken)
                .withOutput(Json.encodeToString(successRequestOutput))
            sfnClient.sendTaskSuccess(sendTaskRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        } else {
            val sendTaskFailureRequest = SendTaskFailureRequest()
                .withTaskToken(request.taskToken)
                .withCause("Flight booking failed")
                .withError("FlightBookingFailed")
            sfnClient.sendTaskFailure(sendTaskFailureRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        }
    }

    @SqsListener(
        value = ["#{flightQueue.flightAppConfig.queueCancelFlight}"],
        deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS,
    )
    fun cancelFlight(msg: String) {
        val request = parseJsonFromStringEither<CancelFlightRequest>(msg)
        log.info("cancelFlight: $request")

        if (cancelFlightSuccess) {
            val sendTaskRequest = SendTaskSuccessRequest()
                .withTaskToken(request.taskToken)
                .withOutput("CancelFlightSuccess".toJsonString())
            sfnClient.sendTaskSuccess(sendTaskRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        } else {
            val sendTaskFailureRequest = SendTaskFailureRequest()
                .withTaskToken(request.taskToken)
                .withCause("Flight cancellation failed")
                .withError("FlightCancellationFailed")
            sfnClient.sendTaskFailure(sendTaskFailureRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        }
    }

    @SqsListener(
        value = ["#{flightQueue.flightAppConfig.queueSendTicket}"],
        deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS,
    )
    fun sendTicket(msg: String) {
        val request = parseJsonFromStringEither<SendPlaneTicketRequest>(msg)
        log.info("sendTicket: $request")

        if (sendTicketSuccess) {
            val sendTaskRequest = SendTaskSuccessRequest()
                .withTaskToken(request.taskToken)
                .withOutput("SendPlaneTicketSuccess".toJsonString())
            sfnClient.sendTaskSuccess(sendTaskRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        } else {
            val sendTaskFailureRequest = SendTaskFailureRequest()
                .withTaskToken(request.taskToken)
                .withCause("Sending plane ticket failed")
                .withError("SendPlaneTicketFailed")
            sfnClient.sendTaskFailure(sendTaskFailureRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        }
    }
}