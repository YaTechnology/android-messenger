package com.github.ndex.messenger.chatgui.presentation.root

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.ndex.messenger.chatgui.di.AppComponent
import com.github.ndex.messenger.chatgui.domain.ChatService
import javax.inject.Inject

class RouterViewModelFactory (appComponent: AppComponent) : ViewModelProvider.Factory {
    @Inject
    lateinit var chatService: ChatService

    init {
        appComponent.inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RouterViewModel::class.java)) {
            return RouterViewModel(chatService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}