package com.github.ndex.messenger.amqpmesenger

interface ChannelBinder {
    fun bind(chatQueue: String)
}