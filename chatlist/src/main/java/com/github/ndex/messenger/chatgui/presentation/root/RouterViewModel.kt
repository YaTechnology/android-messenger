package com.github.ndex.messenger.chatgui.presentation.root

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.github.ndex.messenger.chatgui.domain.ChatService
import com.github.ndex.messenger.chatgui.domain.OnScreenStateChanged
import com.github.ndex.messenger.chatgui.presentation.common.ActionLiveData

/**
 * Controlls main screen, like Router component.
 */
class RouterViewModel(private val chatService: ChatService) : ViewModel() {
    private val _showScreen = ActionLiveData<ScreenState>()
    val showScreen: LiveData<ScreenState> get() = _showScreen

    private val screenStateListener = OnScreenStateChangedListener()
    private var screenBackStack = ArrayList<ScreenState>()

    init {
        chatService.registerScreenChangedListener(screenStateListener)
    }

    /**
     * Called when root view created.
     */
    fun onCreated() {
        if (chatService.shouldShowLoginScreen()) {
            _showScreen.sendAction(ScreenState.LOGIN_SCREEN)
        } else {
            _showScreen.sendAction(ScreenState.CHAT_LIST_SCREEN)
        }
    }

    override fun onCleared() {
        chatService.unregisterScreenStateChangedListener(screenStateListener)
    }

    private inner class OnScreenStateChangedListener : OnScreenStateChanged {
        override fun invoke(screenState: ScreenState) {
            _showScreen.sendAction(screenState)
        }
    }
}