package com.github.ndex.messenger.demo_module.domain

import android.util.Log
import com.github.ndex.messenger.amqpmesenger.AmqpClient
import com.github.ndex.messenger.amqpmesenger.ConnectionFabric
import com.github.ndex.messenger.amqpmesenger.ConsumerFabric
import com.github.ndex.messenger.amqpmesenger.common.AndroidLogger
import com.github.ndex.messenger.amqpmesenger.common.GsonSerializer
import com.github.ndex.messenger.amqpmesenger.common.MainThreadNotifier
import com.github.ndex.messenger.amqpmesenger.messages.ChatMessageHandler
import com.github.ndex.messenger.amqpmesenger.messages.ServiceMessageHandler
import com.github.ndex.messenger.interfaces.*

class ChatService {
    companion object {
        private val TAG = ChatService::class.java.simpleName
        private var sInstance: ChatService? = null

        fun getInstance(): ChatService {
            if (sInstance == null) {
                sInstance = ChatService()
            }
            return sInstance!!
        }
    }

    val client: Client

    constructor() {
        val serializer = GsonSerializer()
        val factory = ConnectionFabric()
        val chatListMessageHandler = ChatMessageHandler()
        val serviceMessageHandler = ServiceMessageHandler(serializer)
        val consumerFabric = ConsumerFabric(serviceMessageHandler, chatListMessageHandler)
        val userId = "11111111111"
        client = AmqpClient(factory,
                consumerFabric,
                serviceMessageHandler,
                chatListMessageHandler,
                MainThreadNotifier(),
                userId,
                GsonSerializer(),
                AndroidLogger())

    }

    fun connect() {
        client.connect()
    }

    fun disconnect() {
        client.disconnect()
    }
}