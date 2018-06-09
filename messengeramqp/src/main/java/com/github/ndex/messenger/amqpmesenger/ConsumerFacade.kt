package com.github.ndex.messenger.amqpmesenger

interface ConsumerFacade {
    fun consume(queue: String) : String
}