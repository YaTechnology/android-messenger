package com.github.ndex.messenger.interfaces

/**
 * Handles new message event.
 */
interface NewMessageListener {
    /**
     * Calls when new message received.
     */
    fun onMessageReceived(message: Message, chatInfo: ChatInfo)
}