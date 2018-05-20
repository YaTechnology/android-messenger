package com.github.ndex.messenger.amqpmesenger.common

import android.util.Log

class AndroidLogger : Logger {
    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun e(tag: String, message: String) {
        Log.e(tag, message)
    }

    override fun e(tag: String, message: String, t: Throwable) {
        Log.e(tag, message, t)
    }
}