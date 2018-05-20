package com.github.ndex.messenger.amqpmesenger

import com.github.ndex.messenger.interfaces.ChatInfo
import com.github.ndex.messenger.interfaces.Message
import com.rabbitmq.client.Channel

interface ChannelMessageSender {
    val channel: Channel

    fun sendMessage(message: Message, chatInfo: ChatInfo)
}