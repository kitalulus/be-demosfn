package com.kl.demostep.hotel.queue

import com.amazonaws.services.stepfunctions.AWSStepFunctions
import com.amazonaws.services.stepfunctions.model.SendTaskFailureRequest
import com.amazonaws.services.stepfunctions.model.SendTaskSuccessRequest
import com.kl.demostep.common.model.BookHotelRequest
import com.kl.demostep.common.model.BookHotelResponse
import com.kl.demostep.common.utils.logger
import com.kl.demostep.common.utils.parseJsonFromStringEither
import com.kl.demostep.hotel.config.HotelAppConfig
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy
import io.awspring.cloud.messaging.listener.annotation.SqsListener
import org.springframework.stereotype.Service

@Service
class HotelQueue(
    val hotelAppConfig: HotelAppConfig,
    val sfnClient: AWSStepFunctions,
) {
    val log = logger()

    private val bookHotelSuccess = true
    private val sendConfirmationSuccess = true

    @SqsListener(
        value = ["#{hotelQueue.hotelAppConfig.queueBookHotel}"],
        deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS,
    )
    fun bookHotel(msg: String) {
        val request = parseJsonFromStringEither<BookHotelRequest>(msg)
        log.info("bookHotel: $request")

        if (bookHotelSuccess) {
            val successRequestOutput = BookHotelResponse(
                bookedHotelId = "HOTEL-${request.orderId}",
            )
            val sendTaskRequest = SendTaskSuccessRequest()
                .withTaskToken(request.taskToken)
                .withOutput(successRequestOutput.toString())
            sfnClient.sendTaskSuccess(sendTaskRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        } else {
            val sendTaskFailureRequest = SendTaskFailureRequest()
                .withTaskToken(request.taskToken)
                .withCause("Hotel booking failed")
                .withError("HotelBookingFailed")
            sfnClient.sendTaskFailure(sendTaskFailureRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        }
    }

    @SqsListener(
        value = ["#{hotelQueue.hotelAppConfig.queueSendConfirmation}"],
        deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS,
    )
    fun sendConfirmation(msg: String) {
        val request = parseJsonFromStringEither<BookHotelRequest>(msg)
        log.info("sendConfirmation: $request")

        if (sendConfirmationSuccess) {
            val sendTaskRequest = SendTaskSuccessRequest()
                .withTaskToken(request.taskToken)
            sfnClient.sendTaskSuccess(sendTaskRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        } else {
            val sendTaskFailureRequest = SendTaskFailureRequest()
                .withTaskToken(request.taskToken)
                .withCause("Send confirmation failed")
                .withError("SendConfirmationFailed")
            sfnClient.sendTaskFailure(sendTaskFailureRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        }
    }
}