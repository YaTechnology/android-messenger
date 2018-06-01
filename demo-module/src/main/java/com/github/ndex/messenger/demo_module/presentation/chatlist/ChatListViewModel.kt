package com.github.ndex.messenger.demo_module.presentation.chatlist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import com.github.ndex.messenger.demo_module.domain.ChatService
import com.github.ndex.messenger.demo_module.presentation.common.ActionLiveData
import com.github.ndex.messenger.interfaces.*


class ChatListViewModel(private val chatService: ChatService) : ViewModel() {
    private val client: Client = chatService.client

    private val _chatList = MutableLiveData<List<ChatInfo>>()
    private val _openChatEvent = ActionLiveData<ChatInfo>()
    val chatList: LiveData<List<ChatInfo>> get() = _chatList
    val openChatEvent: LiveData<ChatInfo> get() = _openChatEvent

    init {
        client.registerConnectionListener(ConnectedListenerImpl())
        client.registerNewMessageListener(MessageReceiverImpl())
        client.registerChatListChangedListener(ChatListChangedListenerImpl())
        client.connect()
    }

    override fun onCleared() {
        client.disconnect()
    }

    fun onItemClicked(item: ChatInfo) {
        chatService.selectChat(item.id, item.name)
        _openChatEvent.sendAction(item)
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
            _chatList.postValue(list)
        }
    }
}