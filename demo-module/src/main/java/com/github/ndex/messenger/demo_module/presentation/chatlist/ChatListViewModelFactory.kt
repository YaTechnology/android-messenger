package com.github.ndex.messenger.demo_module.presentation.chatlist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.ndex.messenger.demo_module.di.AppComponent
import com.github.ndex.messenger.demo_module.domain.ChatService
import javax.inject.Inject

class ChatListViewModelFactory(private val appComponent: AppComponent) : ViewModelProvider.Factory {
    @Inject
    lateinit var chatService: ChatService

    init {
        appComponent.inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatListViewModel::class.java)) {
            return ChatListViewModel(chatService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}