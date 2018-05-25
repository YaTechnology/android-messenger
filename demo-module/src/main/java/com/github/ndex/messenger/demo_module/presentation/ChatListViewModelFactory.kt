package com.github.ndex.messenger.demo_module.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.ndex.messenger.demo_module.domain.ChatService

class ChatListViewModelFactory : ViewModelProvider.Factory {
    private val chatService: ChatService = ChatService.getInstance()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatListViewModel::class.java!!)) {
            return ChatListViewModel(chatService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}