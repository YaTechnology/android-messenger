package com.github.ndex.messenger.demo_module.domain

import com.github.ndex.messenger.amqpmesenger.AmqpClient
import com.github.ndex.messenger.amqpmesenger.AmqpMessage
import com.github.ndex.messenger.amqpmesenger.ConnectionFabric
import com.github.ndex.messenger.amqpmesenger.ConsumerFabric
import com.github.ndex.messenger.amqpmesenger.common.AndroidLogger
import com.github.ndex.messenger.amqpmesenger.common.GsonSerializer
import com.github.ndex.messenger.amqpmesenger.common.MainThreadNotifier
import com.github.ndex.messenger.amqpmesenger.messages.ChatMessageHandler
import com.github.ndex.messenger.amqpmesenger.messages.ServiceMessageHandler
import com.github.ndex.messenger.demo_module.data.HistoryRepository
import com.github.ndex.messenger.interfaces.*
import javax.inject.Inject

typealias OnMessagesListUpdated = (List<Message>) -> Unit

class ChatService @Inject constructor(private val historyRepository: HistoryRepository) {
    companion object {
        private val TAG = ChatService::class.java.simpleName
    }

    val client: Client
    private var currentChatId = ""
    private var messageUpdateObserver = ArrayList<OnMessagesListUpdated>()

    init {
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

        chatListMessageHandler.listener = object : NewMessageListener {
            override fun onMessageReceived(message: Message, chatInfo: ChatInfo) {
                processNewMessage(message)
            }
        }
    }

    fun connect() {
        client.connect()
    }

    fun disconnect() {
        client.disconnect()
    }

    /**
     * Calls when user open chat.
     */
    fun selectChat(chatId: String, name: String) {
        currentChatId = chatId
    }

    fun registerMessagesUpdateObserver(observer: OnMessagesListUpdated) {
        messageUpdateObserver.add(observer)
    }

    fun unregisterMessagesUpdateObserver(observer: OnMessagesListUpdated) {
        messageUpdateObserver.remove(observer)
    }

    fun sendMessage(text: String) {
        val message = buildMessage(text)
        client.sendMessage(message, currentChatId)
        processNewMessage(message)
    }

    private fun buildMessage(text: String) = AmqpMessage(text.toByteArray(), "")

    private fun processNewMessage(message: Message) {
        historyRepository.updateHistory(message)
        historyRepository.requestHistory {
            notifyMessagesListUpdated(it)
        }
    }

    private fun notifyMessagesListUpdated(messageList: List<Message>) {
        messageUpdateObserver.forEach {
            it.invoke(messageList)
        }
    }

    private class MessageUpdateListenerStup : OnMessagesListUpdated {
        override fun invoke(p1: List<Message>) {
            /* stub */
        }
    }
}