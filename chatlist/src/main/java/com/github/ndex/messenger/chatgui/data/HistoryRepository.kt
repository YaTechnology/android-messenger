package com.github.ndex.messenger.chatgui.data

import com.github.ndex.messenger.interfaces.Message

typealias HistoryCallback = (List<Message>) -> Unit

interface HistoryRepository {
    fun requestHistory(callback: HistoryCallback)
    fun updateHistory(message: Message)
}