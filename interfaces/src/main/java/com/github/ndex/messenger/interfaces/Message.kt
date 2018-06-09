package com.github.ndex.messenger.interfaces

import java.util.*

/**
 * Receiving/sending message interface.
 */
interface Message {
    val id: String
    val fromUserId: String
    val chatId: String
    val time: Date
    val body: ByteArray
    val text: String
    var sent: Boolean
    val fromCurrentUser: Boolean
}