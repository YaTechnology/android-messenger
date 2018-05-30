package com.github.ndex.messenger.amqpmesenger.common

interface Logger {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String)
    fun e(tag: String, message: String, t: Throwable)
}