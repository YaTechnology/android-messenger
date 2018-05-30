package com.github.ndex.messenger.amqpmesenger

import com.github.ndex.messenger.amqpmesenger.messages.ChatMessageHandler
import com.github.ndex.messenger.amqpmesenger.messages.ServiceMessageHandler
import com.rabbitmq.client.*
import java.io.IOException


class ConsumerFabric(private val serviceMessageHandler: ServiceMessageHandler,
                     private val chatMessageHandler: ChatMessageHandler) {
    fun provideConsumer(channel: Channel): Consumer {
        return object : DefaultConsumer(channel) {
            @Throws(IOException::class)
            override fun handleDelivery(consumerTag: String,
                                        envelope: Envelope,
                                        properties: AMQP.BasicProperties,
                                        body: ByteArray) {
                val userId = properties.replyTo ?: ""
                if (SERVICE_QUEUE_NAME == userId) {
                    serviceMessageHandler.handleMessage(body)
                } else {
                    chatMessageHandler.handleMessage(body, userId)
                }
            }
        }
    }
}