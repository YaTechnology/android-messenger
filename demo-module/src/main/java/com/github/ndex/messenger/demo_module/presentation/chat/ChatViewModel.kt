package com.github.ndex.messenger.demo_module.presentation.chat

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.ndex.messenger.demo_module.domain.ChatService
import com.github.ndex.messenger.demo_module.domain.OnMessagesListUpdated
import com.github.ndex.messenger.demo_module.presentation.common.ActionLiveData
import com.github.ndex.messenger.interfaces.Message

class ChatViewModel(private val chatService: ChatService) : ViewModel() {
    private val messagesObserver = UpdateMessageList()
    private val _messagesList = MutableLiveData<List<Message>>()
    private val _updateEditTextEvent = ActionLiveData<String>()

    val messagesList: LiveData<List<Message>> get() = _messagesList
    val updateEditTextEvent: LiveData<String> get() = _updateEditTextEvent

    init {
        chatService.registerMessagesUpdateObserver(messagesObserver)
    }

    fun sendMessage(text: String) {
        chatService.sendMessage(text)
        _updateEditTextEvent.sendAction("")
    }

    override fun onCleared() {
        chatService.unregisterMessagesUpdateObserver(messagesObserver)
    }

    private inner class UpdateMessageList : OnMessagesListUpdated {
        override fun invoke(messages: List<Message>) {
            _messagesList.value = messages
        }
    }
}