package com.github.ndex.messenger.amqpmesenger

import com.github.ndex.messenger.interfaces.Message
import com.github.ndex.messenger.interfaces.User
import java.util.*

class AmqpMessage(override val id: String,
                  override val body: ByteArray,
                  override val fromUserId: String,
                  override val chatId: String,
                  override val fromCurrentUser: Boolean = false) : Message {
    override val time = Date()
    override val text: String = String(body)
    override var sent: Boolean = false
}