package com.github.ndex.messenger.android_messenger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import com.github.ndex.messenger.amqpmesenger.AmqpClient
import com.github.ndex.messenger.amqpmesenger.ConnectionFabric
import com.github.ndex.messenger.amqpmesenger.ConsumerFabric
import com.github.ndex.messenger.amqpmesenger.common.AndroidLogger
import com.github.ndex.messenger.amqpmesenger.common.GsonSerializer
import com.github.ndex.messenger.amqpmesenger.common.MainThreadNotifier
import com.github.ndex.messenger.interfaces.*
import java.security.AccessController.getContext

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    lateinit var client: Client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = ConnectionFabric()
        val consumerFabric = ConsumerFabric()
        val userId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID)
        client = AmqpClient(factory,
                consumerFabric,
                MainThreadNotifier(),
                userId,
                GsonSerializer(),
                AndroidLogger())
        client.registerConnectionListener(ConnectedListenerImpl())
        client.registerNewMessageListener(MessageReceiverImpl())
        client.connect()
    }

    override fun onDestroy() {
        super.onDestroy()
        client.disconnect()
    }

    private inner class ConnectedListenerImpl : ConnectionListener {
        override fun onConnected() {
            Log.d(TAG, "onConnected")
        }

        override fun onDisconnected(reason: DisconnectionReasonException) {
            Log.d(TAG, "onDisconnected")
        }
    }

    private inner class MessageReceiverImpl : NewMessageListener {
        override fun onMessageReceived(message: Message, chatInfo: ChatInfo) {
            Log.d(TAG, "onMessageReceived: " + message.text)
        }
    }
}
