package com.github.ndex.messenger.interfaces

import java.util.*

/**
 * Receiving/sending message interface.
 */
interface Message {
    val user: User
    val time: Date
    val body: ByteArray
    val text: String
}