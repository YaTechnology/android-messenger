package com.github.ndex.messenger.amqpmesenger.messages

import com.github.ndex.messenger.amqpmesenger.AmqpMessage
import com.github.ndex.messenger.interfaces.ChatInfo
import com.github.ndex.messenger.interfaces.Message
import com.github.ndex.messenger.interfaces.NewMessageListener
import com.github.ndex.messenger.interfaces.User


class ChatMessageHandler {
    var listener: NewMessageListener = MessageListener()

    fun handleMessage(body: ByteArray, routingKey: String) {
        //TODO: Extract user name from message gson
        val user = User(routingKey, "")
        val users = ArrayList<User>()
        val chatInfo = ChatInfo("", "", users)
        listener.onMessageReceived(AmqpMessage(body, user), chatInfo)
    }

    private class MessageListener : NewMessageListener {
        override fun onMessageReceived(message: Message, chatInfo: ChatInfo) {
            /* stub */
        }
    }
}