package com.github.ndex.messenger.chatgui.presentation.root

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.github.ndex.messenger.chatgui.domain.ChatService
import com.github.ndex.messenger.chatgui.domain.OnScreenStateChanged
import com.github.ndex.messenger.chatgui.presentation.common.ActionLiveData
import java.util.*

/**
 * Controlls main screen, like Router component.
 */
class RootViewModel(private val chatService: ChatService) : ViewModel() {
    private val _showScreen = ActionLiveData<ScreenState>()
    val showScreen: LiveData<ScreenState> get() = _showScreen
    var currentScreen: ScreenState = ScreenState.NONE

    private val screenStateListener = OnScreenStateChangedListener()
    private var screenBackStack = Stack<ScreenState>()

    init {
        chatService.registerScreenChangedListener(screenStateListener)
    }

    /**
     * Called when root view created.
     */
    fun onCreated() {
        chatService.onMainScreenCreated()
    }

    override fun onCleared() {
        chatService.unregisterScreenStateChangedListener(screenStateListener)
    }

    /**
     * return true if overrides back press, false otherwise.
     */
    fun onBackPressed(): Boolean {
        if (screenBackStack.size == 0) {
            return false
        }
        val currentScreen = screenBackStack.pop()
        _showScreen.sendAction(currentScreen)
        return true
    }

    private inner class OnScreenStateChangedListener : OnScreenStateChanged {
        override fun invoke(screenState: ScreenState) {

            if (currentScreen != ScreenState.NONE && currentScreen != ScreenState.LOGIN_SCREEN) {
                screenBackStack.push(currentScreen)
            }
            currentScreen = screenState

            _showScreen.sendAction(screenState)
        }
    }
}