package com.github.ndex.messenger.amqpmesenger

import com.github.ndex.messenger.interfaces.ChatInfo
import com.github.ndex.messenger.interfaces.Message
import com.rabbitmq.client.Channel
import com.rabbitmq.client.AMQP


class ChannelSenderImpl(override val channel: Channel) : ChannelMessageSender {

    override fun sendMessage(message: Message, chatInfo: ChatInfo) {
        val replyProps = AMQP.BasicProperties.Builder()
                //.correlationId(properties.getCorrelationId())
                .build()
        channel.basicPublish(AmqpClient.EXCHANGE_NAME, chatInfo.id, replyProps, message.body)
    }
}