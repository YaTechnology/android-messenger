package com.github.ndex.messenger.demo_module.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import com.github.ndex.messenger.demo_module.domain.ChatService
import com.github.ndex.messenger.interfaces.*


class ChatListViewModel : ViewModel {
    private val chatService: ChatService
    private val client: Client

    constructor(chatService: ChatService) : super() {
        this.chatService = chatService
        client = chatService.client
        client.registerConnectionListener(ConnectedListenerImpl())
        client.registerNewMessageListener(MessageReceiverImpl())
        client.registerChatListChangedListener(ChatListChangedListenerImpl())
        client.connect()
    }

    private val chatList = MutableLiveData<List<ChatInfo>>()

    fun getChatList(): LiveData<List<ChatInfo>> {
        return chatList
    }

    private inner class ConnectedListenerImpl : ConnectionListener {
        override fun onConnected() {
            println("onConnected")
        }

        override fun onDisconnected(reason: DisconnectionReasonException) {
            println("onDisconnected")
        }
    }

    private inner class MessageReceiverImpl : NewMessageListener {
        override fun onMessageReceived(message: Message, chatInfo: ChatInfo) {
            println("onMessageReceived: " + message.text)
        }
    }

    private inner class ChatListChangedListenerImpl : ChatListChangedListener {
        override fun onChatListChanged(list: List<ChatInfo>) {
            println("onChatListChanged: count = ${list.size}")
            chatList.postValue(list)
        }
    }

    override fun onCleared() {
        client.disconnect()
    }
}