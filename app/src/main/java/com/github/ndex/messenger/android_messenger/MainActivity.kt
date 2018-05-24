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
import com.github.ndex.messenger.amqpmesenger.messages.ChatMessageHandler
import com.github.ndex.messenger.amqpmesenger.messages.ServiceMessageHandler
import com.github.ndex.messenger.interfaces.*

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    lateinit var client: Client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val serializer = GsonSerializer()
        val factory = ConnectionFabric()
        val chatListMessageHandler = ChatMessageHandler()
        val serviceMessageHandler = ServiceMessageHandler(serializer)
        val consumerFabric = ConsumerFabric(serviceMessageHandler, chatListMessageHandler)
        val userId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID)
        client = AmqpClient(factory,
                consumerFabric,
                serviceMessageHandler,
                chatListMessageHandler,
                MainThreadNotifier(),
                userId,
                GsonSerializer(),
                AndroidLogger())
        client.registerConnectionListener(ConnectedListenerImpl())
        client.registerNewMessageListener(MessageReceiverImpl())
        client.registerChatListChangedListener(ChatListChangedListenerImpl())
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

    private inner class ChatListChangedListenerImpl : ChatListChangedListener {
        override fun onChatListChanged(list: List<ChatInfo>) {
            Log.d(TAG, "onChatListChanged: count = ${list.size}")
        }
    }
}
