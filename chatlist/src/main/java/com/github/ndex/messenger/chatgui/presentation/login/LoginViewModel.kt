package com.github.ndex.messenger.chatgui.presentation.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.github.ndex.messenger.chatgui.domain.ChatService
import com.github.ndex.messenger.chatgui.presentation.common.ActionLiveData

class LoginViewModel(private val chatService: ChatService) : ViewModel() {
    private val _showLoginScreen = ActionLiveData<Boolean>()
    val showLoginScreen: LiveData<Boolean> get() = _showLoginScreen

    fun onStartAuth(login: String) {
        chatService.auth(login)
        _showLoginScreen.sendAction(false) //TODO: control from service
    }

    /**
     * Called when login screen created.
     */
    fun onCreated() {
        _showLoginScreen.sendAction(chatService.shouldShowLoginScreen())
    }

}