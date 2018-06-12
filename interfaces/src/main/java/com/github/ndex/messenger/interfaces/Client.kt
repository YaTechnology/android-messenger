package com.github.ndex.messenger.interfaces

/**
 * Messanger client interface.
 */
interface Client {
    fun connect()
    fun disconnect()

    fun sendMessage(message: Message)

    fun registerNewMessageListener(listener: NewMessageListener)
    fun unregisterNewMessageListener(listener: NewMessageListener)

    fun registerConnectionListener(listener: ConnectionListener)
    fun unRegisterConnectionListener(listener: ConnectionListener)

    fun registerChatListChangedListener(listener: ChatListChangedListener)
    fun unregisterChatListChangedListener(listener: ChatListChangedListener)
}