package com.github.ndex.messenger.demo_module.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.ndex.messenger.interfaces.ChatInfo
import android.arch.lifecycle.LiveData
import com.github.ndex.messenger.demo_module.domain.ChatService


class ChatListViewModel : ViewModel {
    private val chatService: ChatService

    constructor(chatService: ChatService) : super() {
        this.chatService = chatService
    }

    private val data = MutableLiveData<List<ChatInfo>>()

    fun getChatList(): LiveData<List<ChatInfo>> {
        return data
    }

    override fun onCleared() {

    }
}