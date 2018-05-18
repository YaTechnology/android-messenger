package com.github.ndex.messenger.interfaces

/**
 * Handles chat list changed event.
 */
interface ChatListChangedListener {
    /**
     * Calls when chat list received/modified.
     */
    fun onChatListChanged(list: List<ChatInfo>)
}