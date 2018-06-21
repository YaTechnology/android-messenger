package com.github.ndex.messenger.chatgui.domain

import android.support.annotation.WorkerThread
import com.github.ndex.messenger.amqpmesenger.AmqpClient
import com.github.ndex.messenger.amqpmesenger.AmqpMessage
import com.github.ndex.messenger.amqpmesenger.ConnectionFabric
import com.github.ndex.messenger.amqpmesenger.ConsumerFabric
import com.github.ndex.messenger.amqpmesenger.common.AndroidLogger
import com.github.ndex.messenger.amqpmesenger.common.GsonSerializer
import com.github.ndex.messenger.amqpmesenger.common.MainThreadNotifier
import com.github.ndex.messenger.amqpmesenger.messages.ChatMessageHandler
import com.github.ndex.messenger.amqpmesenger.messages.ServiceMessageHandler
import com.github.ndex.messenger.chatgui.data.HistoryRepository
import com.github.ndex.messenger.chatgui.data.SettingsRepository
import com.github.ndex.messenger.chatgui.presentation.root.ScreenState
import com.github.ndex.messenger.interfaces.*
import java.util.*
import javax.inject.Inject

typealias OnMessagesListUpdated = (List<Message>) -> Unit
typealias OnScreenStateChanged = (ScreenState) -> Unit

class ChatService @Inject constructor(private val historyRepository: HistoryRepository,
                                      private val settings: SettingsRepository) {

    lateinit var client: Client
    private var currentChatId = ""
    private var messageUpdateObserver = ArrayList<OnMessagesListUpdated>()
    private var router: OnScreenStateChanged = OnScreenStateChangedStub()

    fun disconnect() {
        client.disconnect()
    }

    /**
     * Calls when user open chat.
     */
    fun selectChat(chatId: String, name: String) {
        currentChatId = chatId
        router.invoke(ScreenState.CHAT_SCREEN)
    }

    fun registerMessagesUpdateObserver(observer: OnMessagesListUpdated) {
        messageUpdateObserver.add(observer)
    }

    fun unregisterMessagesUpdateObserver(observer: OnMessagesListUpdated) {
        messageUpdateObserver.remove(observer)
    }

    fun registerNewMessageListener(listener: NewMessageListener) {
        client.registerNewMessageListener(listener)
    }

    fun registerChatListChangedListener(listener: ChatListChangedListener) {
        client.registerChatListChangedListener(listener)
    }

    fun registerScreenChangedListener(listener: OnScreenStateChanged) {
        router = listener
    }

    fun unregisterScreenStateChangedListener(listener: OnScreenStateChanged) {
        router = OnScreenStateChangedStub()
    }

    fun sendMessage(text: String) {
        val message = buildMessage(text)
        client.sendMessage(message)
        processNewMessage(message)
    }

    private fun buildMessage(text: String) =
            AmqpMessage(id = UUID.randomUUID().toString(),
                    body = text.toByteArray(),
                    fromUserId = settings.requestLogin(),
                    chatId = currentChatId,
                    fromCurrentUser = true)

    @WorkerThread
    private fun notifyMessagesListUpdated(messageList: List<Message>) {
        messageUpdateObserver.forEach {
            it.invoke(messageList)
        }
    }

    fun shouldShowLoginScreen(): Boolean {
        if (settings.requestLogin() != "") {
            connect()
            return false
        }
        return true
    }

    fun auth(login: String) {
        settings.storeLogin(login)
        connect()
        router.invoke(ScreenState.CHAT_LIST_SCREEN)
    }

    private fun connect() {
        val log = AndroidLogger()
        val serializer = GsonSerializer()
        val factory = ConnectionFabric()
        val chatListMessageHandler = ChatMessageHandler()
        val serviceMessageHandler = ServiceMessageHandler(serializer)
        val consumerFabric = ConsumerFabric(serviceMessageHandler, chatListMessageHandler, log)
        val userId = settings.requestLogin()
        client = AmqpClient(factory,
                consumerFabric,
                serviceMessageHandler,
                chatListMessageHandler,
                MainThreadNotifier(),
                userId,
                GsonSerializer(),
                log)

        chatListMessageHandler.listener = object : NewMessageListener {
            override fun onMessageReceived(message: Message, chatInfo: ChatInfo) {
                processNewMessage(message)
            }
        }
        client.connect()
    }

    private fun processNewMessage(message: Message) {
        historyRepository.updateHistory(message)
        historyRepository.requestHistory {
            notifyMessagesListUpdated(it)
        }
    }

    fun onMainScreenCreated() {
        if (shouldShowLoginScreen()) {
            router.invoke(ScreenState.LOGIN_SCREEN)
        } else {
            router.invoke(ScreenState.CHAT_LIST_SCREEN)
        }
    }

    private class MessageUpdateListenerStub : OnMessagesListUpdated {
        override fun invoke(p1: List<Message>) {
            /* stub */
        }
    }

    private class OnScreenStateChangedStub : OnScreenStateChanged {
        override fun invoke(p1: ScreenState) {
            /* stub */
        }
    }

    companion object {
        private val TAG = ChatService::class.java.simpleName
    }
}