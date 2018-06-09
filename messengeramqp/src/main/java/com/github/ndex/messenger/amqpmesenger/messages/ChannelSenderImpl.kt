package com.github.ndex.messenger.amqpmesenger.messages

import com.github.ndex.messenger.amqpmesenger.ChannelMessageSender
import com.github.ndex.messenger.amqpmesenger.EXCHANGE_NAME
import com.github.ndex.messenger.interfaces.Message
import com.rabbitmq.client.Channel
import com.rabbitmq.client.AMQP

const val MESSAGE_HEADER_CHAT_ID = "CHAT_ID"

class ChannelSenderImpl(private val channel: Channel) : ChannelMessageSender {
    override fun sendMessage(message: Message) {
        val headers = HashMap<String, Any>()
        headers[MESSAGE_HEADER_CHAT_ID] = message.chatId

        val replyProps = AMQP.BasicProperties.Builder()
                .replyTo(message.fromUserId)
                .correlationId(message.id)
                .headers(headers)
                .build()
        channel.basicPublish(EXCHANGE_NAME, message.chatId, replyProps, message.body)
    }
}