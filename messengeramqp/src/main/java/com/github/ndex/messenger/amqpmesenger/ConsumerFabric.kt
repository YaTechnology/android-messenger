package com.github.ndex.messenger.amqpmesenger

import com.github.ndex.messenger.amqpmesenger.common.Logger
import com.github.ndex.messenger.amqpmesenger.messages.ChatMessageHandler
import com.github.ndex.messenger.amqpmesenger.messages.MESSAGE_HEADER_CHAT_ID
import com.github.ndex.messenger.amqpmesenger.messages.ServiceMessageHandler
import com.rabbitmq.client.*
import com.rabbitmq.client.impl.LongStringHelper
import java.io.IOException


class ConsumerFabric(private val serviceMessageHandler: ServiceMessageHandler,
                     private val chatMessageHandler: ChatMessageHandler,
                     private val log: Logger) {
    fun provideConsumer(channel: Channel): Consumer {
        return object : DefaultConsumer(channel) {
            @Throws(IOException::class)
            override fun handleDelivery(consumerTag: String,
                                        envelope: Envelope,
                                        properties: AMQP.BasicProperties,
                                        body: ByteArray) {
                val userId = properties.replyTo ?: ""
                log.d(TAG, "handleDelivery from $userId")
                try {
                    if (SERVICE_QUEUE_NAME == userId) {
                        serviceMessageHandler.handleMessage(body)
                    } else {
                        val chatId = getChatIdFromHeader(properties.headers)
                        chatMessageHandler.handleMessage(properties.correlationId,
                                userId, chatId, body)
                    }
                } catch (ex: Exception) {
                    log.e(TAG, "handleDelivery: ", ex)
                }
            }
        }
    }

    private fun getChatIdFromHeader(headers: MutableMap<String, Any>?): String {
        if (headers == null) {
            return ""
        }
        val chatId = headers[MESSAGE_HEADER_CHAT_ID]
        if (chatId is LongString) {
            return chatId.toString()
        }
        return ""
    }

    companion object {
        private val TAG = ConsumerFabric::class.java.simpleName
    }
}