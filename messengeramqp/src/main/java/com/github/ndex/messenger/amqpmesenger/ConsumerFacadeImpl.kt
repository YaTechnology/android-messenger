package com.github.ndex.messenger.amqpmesenger

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Consumer

class ConsumerFacadeImpl(private val channel: Channel,
                         private val consumer: Consumer) : ConsumerFacade {
    override fun consume(queue: String) =
        channel.basicConsume(queue, consumer)

}