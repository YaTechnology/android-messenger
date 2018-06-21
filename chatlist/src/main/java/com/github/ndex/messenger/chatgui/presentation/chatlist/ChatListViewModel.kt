package com.github.ndex.messenger.chatgui.presentation.chatlist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import com.github.ndex.messenger.chatgui.domain.ChatService
import com.github.ndex.messenger.chatgui.presentation.common.ActionLiveData
import com.github.ndex.messenger.interfaces.*


class ChatListViewModel(private val chatService: ChatService) : ViewModel() {
    private val _chatList = MutableLiveData<List<ChatInfo>>()
    val chatList: LiveData<List<ChatInfo>> get() = _chatList

    init {
        chatService.registerNewMessageListener(MessageReceiverImpl())
        chatService.registerChatListChangedListener(ChatListChangedListenerImpl())
    }

    override fun onCleared() {
        chatService.disconnect()
    }

    fun onItemClicked(item: ChatInfo) {
        chatService.selectChat(item.id, item.name)
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