package com.github.ndex.messenger.demo_module.presentation.chat

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.ndex.messenger.demo_module.di.AppComponent
import com.github.ndex.messenger.demo_module.domain.ChatService
import javax.inject.Inject

class ChatViewModelFactory(appComponent: AppComponent) : ViewModelProvider.Factory {
    @Inject
    lateinit var chatService: ChatService

    init {
        appComponent.inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(chatService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}