package com.github.ndex.messenger.interfaces

/**
 * Connection status listener.
 */
interface ConnectionListener {
    fun onConnected()
    fun onDisconnected(reason: DisconnectionReasonException)
}