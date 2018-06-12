package com.github.ndex.messenger.amqpmesenger.messages

import com.github.ndex.messenger.amqpmesenger.AmqpMessage
import com.github.ndex.messenger.interfaces.ChatInfo
import com.github.ndex.messenger.interfaces.Message
import com.github.ndex.messenger.interfaces.NewMessageListener
import com.github.ndex.messenger.interfaces.User


class ChatMessageHandler {
    var listener: NewMessageListener = MessageListener()

    fun handleMessage(messageId: String,
                      fromUserId: String,
                      fromChatId: String,
                      body: ByteArray) {
        //TODO: Extract user name from message gson
        val user = User(fromUserId, "")
        val users = ArrayList<User>()
        val chatInfo = ChatInfo(fromChatId, "", users)

        val message = AmqpMessage(
                id = messageId,
                body = body,
                fromUserId = fromUserId,
                chatId = fromChatId)

        listener.onMessageReceived(message, chatInfo)
    }

    private class MessageListener : NewMessageListener {
        override fun onMessageReceived(message: Message, chatInfo: ChatInfo) {
            /* stub */
        }
    }
}