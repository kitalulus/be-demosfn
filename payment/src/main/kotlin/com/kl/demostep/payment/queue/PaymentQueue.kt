package com.kl.demostep.payment.queue

import com.amazonaws.services.stepfunctions.AWSStepFunctions
import com.kl.demostep.common.model.HoldPaymentRequest
import com.kl.demostep.common.utils.logger
import com.kl.demostep.common.utils.parseJsonFromStringEither
import com.kl.demostep.payment.config.PaymentAppConfig
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy
import io.awspring.cloud.messaging.listener.annotation.SqsListener
import org.springframework.stereotype.Service

@Service
class PaymentQueue(
    val paymentAppConfig: PaymentAppConfig,
    val sfnClient: AWSStepFunctions,
) {
    val log = logger()

    @SqsListener(
        value = ["#{paymentQueue.paymentAppConfig.queueHoldPayment}"],
        deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS,
    )
    fun holdPayment(msg: String) {
        val request = parseJsonFromStringEither<HoldPaymentRequest>(msg)
        log.info("holdPayment: $request")

    }

    @SqsListener(
        value = ["#{paymentQueue.paymentAppConfig.queueSettlePayment}"],
        deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS,
    )
    fun settlePayment(msg: String) {
        log.info("settlePayment")
    }

    @SqsListener(
        value = ["#{paymentQueue.paymentAppConfig.queueCancelPayment}"],
        deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS,
    )
    fun cancelPayment(msg: String) {
        log.info("cancelPayment")
    }

    @SqsListener(
        value = ["#{paymentQueue.paymentAppConfig.queueSendInvoice}"],
        deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS,
    )
    fun sendInvoice(msg: String) {
        log.info("sendInvoice")
    }
}