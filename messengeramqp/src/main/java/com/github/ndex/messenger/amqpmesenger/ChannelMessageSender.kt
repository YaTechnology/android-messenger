package com.github.ndex.messenger.amqpmesenger

import com.github.ndex.messenger.interfaces.Message
import com.rabbitmq.client.Channel

interface ChannelMessageSender {
    fun sendMessage(message: Message)
}