package com.github.ndex.messenger.chatgui.data

import android.content.Context
import com.github.ndex.messenger.interfaces.Message
import java.util.*
import kotlin.Comparator

class HistoryRepositoryImpl(private val appContext: Context) : HistoryRepository {
    private val messagesList = TreeSet<Message>(MessageComparator())

    override fun requestHistory(callback: HistoryCallback) {
        callback.invoke(messagesList.toList())
    }

    override fun updateHistory(message: Message) {
        if (!messagesList.add(message)) {
            message.sent = true
        }
    }
}

private class MessageComparator : Comparator<Message> {
    override fun compare(o1: Message, o2: Message): Int {
        if (o1.id == o2.id) {
            return 0
        }
        return -o1.time.compareTo(o2.time)
    }
}