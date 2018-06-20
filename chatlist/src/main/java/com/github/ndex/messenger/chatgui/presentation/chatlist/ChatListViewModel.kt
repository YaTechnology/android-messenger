package com.github.ndex.messenger.chatgui.presentation.chatlist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import com.github.ndex.messenger.chatgui.domain.ChatService
import com.github.ndex.messenger.chatgui.presentation.common.ActionLiveData
import com.github.ndex.messenger.interfaces.*


class ChatListViewModel(private val chatService: ChatService) : ViewModel() {
    private val _chatList = MutableLiveData<List<ChatInfo>>()
    private val _openChatEvent = ActionLiveData<ChatInfo>()
    val chatList: LiveData<List<ChatInfo>> get() = _chatList
    val openChatEvent: LiveData<ChatInfo> get() = _openChatEvent

    init {
        chatService.registerNewMessageListener(MessageReceiverImpl())
        chatService.registerChatListChangedListener(ChatListChangedListenerImpl())
    }

    override fun onCleared() {
        chatService.disconnect()
    }

    fun onItemClicked(item: ChatInfo) {
        chatService.selectChat(item.id, item.name)
        _openChatEvent.sendAction(item)
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