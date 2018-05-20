package com.github.ndex.messenger.amqpmesenger

import com.github.ndex.messenger.interfaces.ChatInfo
import com.github.ndex.messenger.interfaces.Message
import com.rabbitmq.client.Channel

class ChannelSenderStub : ChannelMessageSender {
    override val channel: Channel
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun sendMessage(message: Message, chatInfo: ChatInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}