package com.kl.demostep.common.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.stepfunctions.AWSStepFunctions
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StepFunctionConfig {
    @Value("\${cloud.aws.credentials.access-key}")
    private lateinit var accessKey: String

    @Value("\${cloud.aws.credentials.secret-key}")
    private lateinit var secretKey: String

    @Bean
    fun sfnClient(): AWSStepFunctions {
        val credential = BasicAWSCredentials(accessKey, secretKey)
        return AWSStepFunctionsClientBuilder
            .standard()
            .withCredentials(AWSStaticCredentialsProvider(credential))
            .build()
    }
}