package com.github.ndex.messenger.amqpmesenger

import com.github.ndex.messenger.interfaces.ChatInfo
import com.github.ndex.messenger.interfaces.Message
import com.github.ndex.messenger.interfaces.NewMessageListener
import com.github.ndex.messenger.interfaces.User
import com.rabbitmq.client.*
import java.io.IOException


class ConsumerFabric {
    var listener: NewMessageListener = MessageListener()

    fun provideConsumer(channel: Channel): Consumer {
        return object : DefaultConsumer(channel) {
            @Throws(IOException::class)
            override fun handleDelivery(consumerTag: String,
                                        envelope: Envelope,
                                        properties: AMQP.BasicProperties,
                                        body: ByteArray) {
                try {
                    val user = User("", "")
                    val users = ArrayList<User>()
                    val chatInfo = ChatInfo("", users)
                    listener.onMessageReceived(AmqpMessage(body, user), chatInfo)
                } catch (e: RuntimeException) {
                    println(" [.] " + e.toString())
                } finally {
                    channel.basicAck(envelope.deliveryTag, false)
                }
            }
        }
    }

    private inner class MessageListener : NewMessageListener {
        override fun onMessageReceived(message: Message, chatInfo: ChatInfo) {
            /* stub */
        }
    }
}