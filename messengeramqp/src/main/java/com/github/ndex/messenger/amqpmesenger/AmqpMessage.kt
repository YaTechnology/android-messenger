package com.github.ndex.messenger.amqpmesenger

import com.github.ndex.messenger.interfaces.Message
import com.github.ndex.messenger.interfaces.User
import java.util.*

class AmqpMessage(override val body: ByteArray,
                  override val fromChatId: String) : Message {
    override val time = Date()
    override val text: String = String(body)
}