package com.github.ndex.messenger.amqpmesenger

import com.github.ndex.messenger.interfaces.Message
import com.rabbitmq.client.Channel
import com.rabbitmq.client.AMQP


class ChannelSenderImpl(override val channel: Channel) : ChannelMessageSender {

    override fun sendMessage(message: Message, chatId: String) {
        val replyProps = AMQP.BasicProperties.Builder().build()
        channel.basicPublish(EXCHANGE_NAME, chatId, replyProps, message.body)
    }
}