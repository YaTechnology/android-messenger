package com.github.ndex.messenger.amqpmesenger.common

import android.support.annotation.WorkerThread
import com.github.ndex.messenger.amqpmesenger.ChannelBinder
import com.github.ndex.messenger.amqpmesenger.EXCHANGE_NAME
import com.rabbitmq.client.Channel

/**
 * Binds to chat queue.
 */
class ChannelBinderImpl(private val uuid: String,
                        private val channel: Channel,
                        private val log: Logger) : ChannelBinder {
    private val boundQueues = ArrayList<String>()

    @WorkerThread
    override fun bind(chatQueue: String) {
        if (boundQueues.contains(chatQueue)) {
            return
        }

        boundQueues.add(chatQueue)
        channel.queueBind(uuid, EXCHANGE_NAME, chatQueue)
        log.d(TAG, "bind to $chatQueue")
    }

    companion object {
        private val TAG = ChannelBinderImpl::class.java.simpleName
    }
}