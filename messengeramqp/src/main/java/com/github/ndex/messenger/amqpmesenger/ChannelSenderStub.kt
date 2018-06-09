package com.github.ndex.messenger.amqpmesenger

import com.github.ndex.messenger.interfaces.Message
import com.rabbitmq.client.Channel

class ChannelSenderStub : ChannelMessageSender {
    override fun sendMessage(message: Message) {
        /* stub */
    }
}