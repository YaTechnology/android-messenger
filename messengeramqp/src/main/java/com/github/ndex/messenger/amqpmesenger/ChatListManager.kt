package com.github.ndex.messenger.amqpmesenger

import com.github.ndex.messenger.amqpmesenger.common.Serializer
import com.github.ndex.messenger.amqpmesenger.models.ChatListCommand
import com.github.ndex.messenger.amqpmesenger.models.ChatListCommand.Companion.GET_CHAT_LIST
import com.rabbitmq.client.Channel
import com.rabbitmq.client.AMQP
import java.util.*


class ChatListManager(private val serializer: Serializer,
                      private val channel: Channel,
                      private val uuid: String) {
    fun requestChatList() {
        val cmd = ChatListCommand(GET_CHAT_LIST, uuid)
        val bytes = serializer.serialize(cmd)

        val replyProps = AMQP.BasicProperties.Builder()
                .replyTo(uuid)
                .correlationId(UUID.randomUUID().toString())
                .build()

        channel.basicPublish(AmqpClient.EXCHANGE_NAME,
                AmqpClient.SERVICE_QUEUE_NAME, replyProps, bytes)
    }
}