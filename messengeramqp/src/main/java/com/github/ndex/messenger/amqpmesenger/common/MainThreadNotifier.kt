package com.github.ndex.messenger.amqpmesenger.common

import android.os.Handler
import android.os.Looper

/**
 * Wrapper for handler.
 */
class MainThreadNotifier {
    private val handler: Handler = Handler(Looper.getMainLooper())

    fun runOnUiThread(runnable: Runnable) {
        handler.post(runnable)
    }
}