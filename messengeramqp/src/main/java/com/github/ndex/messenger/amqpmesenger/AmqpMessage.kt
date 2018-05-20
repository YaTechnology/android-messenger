package com.github.ndex.messenger.amqpmesenger

import com.github.ndex.messenger.interfaces.Message
import com.github.ndex.messenger.interfaces.User
import java.util.*

class AmqpMessage : Message {
    private val body: ByteArray
    private val text: String
    private val user: User
    private val date: Date

    constructor(body: ByteArray, user: User) {
        this.body = body
        this.text = String(body)
        this.user = user
        date = Date()
    }

    override fun getUser(): User {
        return user
    }

    override fun getTime(): Date {
        return date
    }

    override fun getBody(): ByteArray {
        return body
    }

    override fun getText(): String {
        return text
    }
}