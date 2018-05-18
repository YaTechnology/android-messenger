package com.github.ndex.messenger.interfaces

import java.util.*

/**
 * Receiving/sending message interface.
 */
interface Message {
    fun getUser(): User
    fun getTime(): Date
    fun getBody(): ByteArray
    fun getText(): String
}