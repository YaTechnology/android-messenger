package com.github.ndex.messenger.interfaces

interface Client {
    fun connect()
    fun disconnect()

    fun registerNewMessageListener(listener: NewMessageListener)
    fun unregisterNewMessageListener(listener: NewMessageListener)

    fun registerConnectionListener(listener: ConnectionListener)
    fun unRegisterConnectionListener(listener: ConnectionListener)
}