package com.github.ndex.messenger.chatgui.presentation.login

import android.arch.lifecycle.ViewModel
import com.github.ndex.messenger.chatgui.domain.ChatService

class LoginViewModel(private val chatService: ChatService) : ViewModel() {
    fun onStartAuth(login: String) {
        chatService.auth(login)
    }
}