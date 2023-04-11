package com.kl.demostep.payment.queue

import com.amazonaws.services.stepfunctions.AWSStepFunctions
import com.amazonaws.services.stepfunctions.model.SendTaskFailureRequest
import com.amazonaws.services.stepfunctions.model.SendTaskSuccessRequest
import com.kl.demostep.common.model.*
import com.kl.demostep.common.utils.logger
import com.kl.demostep.common.utils.parseJsonFromStringEither
import com.kl.demostep.common.utils.toJsonString
import com.kl.demostep.payment.config.PaymentAppConfig
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy
import io.awspring.cloud.messaging.listener.annotation.SqsListener
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Service

@Service
class PaymentQueue(
    val paymentAppConfig: PaymentAppConfig,
    val sfnClient: AWSStepFunctions,
) {
    val log = logger()

    private val holdPaymentSuccess = true
    private val settlePaymentSuccess = true
    private val cancelPaymentSuccess = true
    private val sendInvoiceSuccess = true

    @SqsListener(
        value = ["#{paymentQueue.paymentAppConfig.queueHoldPayment}"],
        deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS,
    )
    fun holdPayment(msg: String) {
        val request = parseJsonFromStringEither<HoldPaymentRequest>(msg)
        log.info("holdPayment: $request")
        if (holdPaymentSuccess) {
            val successRequestOutput = HoldPaymentResponse(
                paymentId = "PAYMENT-${request.orderId}",
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
                .withCause("Payment failed")
                .withError("PaymentFailed")
            sfnClient.sendTaskFailure(sendTaskFailureRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        }
    }

    @SqsListener(
        value = ["#{paymentQueue.paymentAppConfig.queueSettlePayment}"],
        deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS,
    )
    fun settlePayment(msg: String) {
        val request = parseJsonFromStringEither<SettlePaymentRequest>(msg)
        log.info("settlePayment: $request")
        if (settlePaymentSuccess) {
            val sendTaskRequest = SendTaskSuccessRequest()
                .withTaskToken(request.taskToken)
                .withOutput("SettlePaymentSuccess".toJsonString())
            sfnClient.sendTaskSuccess(sendTaskRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        } else {
            val sendTaskFailureRequest = SendTaskFailureRequest()
                .withTaskToken(request.taskToken)
                .withCause("Settle payment failed")
                .withError("SettleFailed")
            sfnClient.sendTaskFailure(sendTaskFailureRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        }
    }

    @SqsListener(
        value = ["#{paymentQueue.paymentAppConfig.queueCancelPayment}"],
        deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS,
    )
    fun cancelPayment(msg: String) {
        val request = parseJsonFromStringEither<ReleaseHoldRequest>(msg)
        log.info("cancelPayment: $request")
        if (cancelPaymentSuccess) {
            val sendTaskRequest = SendTaskSuccessRequest()
                .withTaskToken(request.taskToken)
                .withOutput("CancelPaymentSuccess".toJsonString())
            sfnClient.sendTaskSuccess(sendTaskRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        } else {
            val sendTaskFailureRequest = SendTaskFailureRequest()
                .withTaskToken(request.taskToken)
                .withCause("Cancel payment failed")
                .withError("CancelFailed")
            sfnClient.sendTaskFailure(sendTaskFailureRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        }
    }

    @SqsListener(
        value = ["#{paymentQueue.paymentAppConfig.queueSendInvoice}"],
        deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS,
    )
    fun sendInvoice(msg: String) {
        val request = parseJsonFromStringEither<SendInvoiceRequest>(msg)
        log.info("sendInvoice: $request")
        if (sendInvoiceSuccess) {
            val sendTaskRequest = SendTaskSuccessRequest()
                .withTaskToken(request.taskToken)
                .withOutput("SendInvoiceSuccess".toJsonString())
            sfnClient.sendTaskSuccess(sendTaskRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        } else {
            val sendTaskFailureRequest = SendTaskFailureRequest()
                .withTaskToken(request.taskToken)
                .withCause("Send invoice failed")
                .withError("SendInvoiceFailed")
            sfnClient.sendTaskFailure(sendTaskFailureRequest)
                .also {
                    assert(it.sdkHttpMetadata.httpStatusCode == 200)
                }
        }
    }
}