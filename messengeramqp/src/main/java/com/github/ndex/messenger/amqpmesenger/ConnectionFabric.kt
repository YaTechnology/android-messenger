package com.github.ndex.messenger.amqpmesenger

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory

class ConnectionFabric {
    private val factory: ConnectionFactory
    val connection: Connection
        get() = factory.newConnection()

    constructor() {
        factory = ConnectionFactory()
        factory.host = "10.0.2.2"
    }
}