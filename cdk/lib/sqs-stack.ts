import {
  Stack,
  StackProps,
  Duration
}from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as sqs from 'aws-cdk-lib/aws-sqs';

export class SqsStack extends Stack {
  constructor(scope: Construct, id: string, props: StackProps) {
    super(scope, id, props);
    const {
      NAME,
      BOOK_FLIGHT_SQS_NAME,
      BOOK_HOTEL_SQS_NAME,
      CANCEL_PAYMENT_SQS_NAME,
      CANCEL_BOOK_SQS_NAME,
      CANCEL_FLIGHT_SQS_NAME,
      HOLD_PAYMENT_SQS_NAME,
      SEND_INVOICE_SQS_NAME,
      SEND_HOTEL_CONFIRMATION_SQS_NAME,
      SEND_PLANE_TICKET_SQS_NAME,
      SETTLE_PAYMENT_SQS_NAME
    } = process.env

    const duration = Duration.seconds(300)
    
    new sqs.Queue(this, `BookFlightQueue${NAME}`, {
      queueName: `${NAME}_${BOOK_FLIGHT_SQS_NAME}`,
      visibilityTimeout: duration,
    });

    new sqs.Queue(this, `BookHotelQueue${NAME}`, {
      queueName: `${NAME}_${BOOK_HOTEL_SQS_NAME}`,
      visibilityTimeout: duration,
    });

    new sqs.Queue(this, `CancelPaymentQueue${NAME}`, {
      queueName: `${NAME}_${CANCEL_PAYMENT_SQS_NAME}`,
      visibilityTimeout: duration,
    });

    new sqs.Queue(this, `CancelBookQueue${NAME}`, {
      queueName: `${NAME}_${CANCEL_BOOK_SQS_NAME}`,
      visibilityTimeout: duration,
    });

    new sqs.Queue(this, `CancelFlightQueue${NAME}`, {
      queueName: `${NAME}_${CANCEL_FLIGHT_SQS_NAME}`,
      visibilityTimeout: duration,
    });

    new sqs.Queue(this, `HoldPaymentQueue${NAME}`, {
      queueName: `${NAME}_${HOLD_PAYMENT_SQS_NAME}`,
      visibilityTimeout: duration,
    });

    new sqs.Queue(this, `SendInvoiceQueue${NAME}`, {
      queueName: `${NAME}_${SEND_INVOICE_SQS_NAME}`,
      visibilityTimeout: duration,
    });

    new sqs.Queue(this, `SendHotelConfirmationQueue${NAME}`, {
      queueName: `${NAME}_${SEND_HOTEL_CONFIRMATION_SQS_NAME}`,
      visibilityTimeout: duration,
    });

    new sqs.Queue(this, `SendPlaneTicketQueue${NAME}`, {
      queueName: `${NAME}_${SEND_PLANE_TICKET_SQS_NAME}`,
      visibilityTimeout: duration,
    });

    new sqs.Queue(this, `SettlePaymentQueue${NAME}`, {
      queueName: `${NAME}_${SETTLE_PAYMENT_SQS_NAME}`,
      visibilityTimeout: duration,
    });

  }
}
