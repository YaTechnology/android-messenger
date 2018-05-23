package com.github.ndex.messenger.amqpmesenger

import com.github.ndex.messenger.amqpmesenger.common.Logger
import com.github.ndex.messenger.amqpmesenger.common.MainThreadNotifier
import com.github.ndex.messenger.amqpmesenger.common.Serializer
import com.github.ndex.messenger.interfaces.*
import com.rabbitmq.client.Connection

class AmqpClient(private val factory: ConnectionFabric,
                 private val consumerFabric: ConsumerFabric,
                 private val uiRunner: MainThreadNotifier,
                 private val uuid: String,
                 private val serializer: Serializer,
                 logger: Logger) : Client {
    companion object {
        private val TAG = AmqpClient::class.java.simpleName
        val EXCHANGE_NAME = "messenger.topic"
        val SERVICE_QUEUE_NAME = "services"
    }

    private var connection: Connection = ConectionStub()
    private var channelSender: ChannelMessageSender = ChannelSenderStub()
    private val messageListeners = ArrayList<NewMessageListener>()
    private val connectionListeners = ArrayList<ConnectionListener>()
    private val log: Logger = logger

    override fun connect() = Thread { doConnect() }.start()

    private fun doConnect() {
        try {
            connection = factory.connection
            val channel = connection.createChannel(666)
            channel.exchangeDeclare(EXCHANGE_NAME, "topic")
            channel.queueDeclare(uuid, false, true, false, null)
            channel.basicQos(1)
            channel.queueBind(uuid, EXCHANGE_NAME, uuid)
            channelSender = ChannelSenderImpl(channel)

            val consumer = consumerFabric.provideConsumer(channel)
            val tag = channel.basicConsume(uuid, false, consumer)
            log.d(TAG, "consume = $tag")

            notifyConnected()

            val chatListManager = ChatListManager(serializer, channel, uuid)
            chatListManager.requestChatList()
        } catch (e: Exception) {
            log.e(TAG, "doConnect: ", e)
        }
    }

    private fun notifyConnected() {
        uiRunner.runOnUiThread(Runnable {
            connectionListeners.forEach {
                it.onConnected()
            }
        })
    }

    override fun disconnect() {
        try {
            connection.close()
        } catch (e: Exception) {
            log.e(TAG, "disconnect: ", e)
        }
    }

    override fun sendMessage(message: Message, chatInfo: ChatInfo) {
        channelSender.sendMessage(message, chatInfo)
    }

    override fun registerNewMessageListener(listener: NewMessageListener) {
        messageListeners.add(listener)
    }

    override fun unregisterNewMessageListener(listener: NewMessageListener) {
        messageListeners.remove(listener)
    }

    override fun registerConnectionListener(listener: ConnectionListener) {
        connectionListeners.add(listener)
    }

    override fun unRegisterConnectionListener(listener: ConnectionListener) {
        connectionListeners.remove(listener)
    }

    private inner class MessageListener : NewMessageListener {
        override fun onMessageReceived(message: Message, chatInfo: ChatInfo) {
            messageListeners.forEach {
                it.onMessageReceived(message, chatInfo)
            }
        }
    }

    init {
        consumerFabric.listener = MessageListener()
    }
}