package com.github.ndex.messenger.demo_module.data

import android.content.Context
import com.github.ndex.messenger.interfaces.Message

class HistoryRepositoryImpl(private val appContext: Context) : HistoryRepository {
    private val messagesList = ArrayList<Message>()

    override fun requestHistory(callback: HistoryCallback) {
        callback.invoke(messagesList)
    }

    override fun updateHistory(message: Message) {
        messagesList.add(message)
    }
}