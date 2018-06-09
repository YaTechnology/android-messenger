package com.github.ndex.messenger.amqpmesenger.stubs

import com.github.ndex.messenger.amqpmesenger.ConsumerFacade

class ConsumerFacadeStub : ConsumerFacade {
    override fun consume(queue: String): String {
        /* stub */
        return ""
    }
}