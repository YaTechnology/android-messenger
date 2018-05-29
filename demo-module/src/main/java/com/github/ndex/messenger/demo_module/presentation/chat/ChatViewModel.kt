package com.github.ndex.messenger.demo_module.presentation.chat

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.ndex.messenger.demo_module.domain.ChatService
import com.github.ndex.messenger.interfaces.Client
import com.github.ndex.messenger.interfaces.Message

class ChatViewModel(private val chatService: ChatService, private val chatId: String) : ViewModel() {
    private val client: Client = chatService.client
    private val messagesList = MutableLiveData<List<Message>>()

    //fun getMessagesList(): LiveData<List<Message>> {
    //    return chatService.
    //}

    fun sendMessage(text: String) {
        //client.sendMessage()
    }
}