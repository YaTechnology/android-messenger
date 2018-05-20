package com.github.ndex.messenger.amqpmesenger

import com.github.ndex.messenger.amqpmesenger.common.Logger
import com.github.ndex.messenger.amqpmesenger.common.MainThreadNotifier
import com.github.ndex.messenger.interfaces.*
import com.rabbitmq.client.Connection

class AmqpClient : Client {
    companion object {
        private val TAG = AmqpClient::class.java.simpleName
        private val QUEUE_NAME = "queue"
    }

    private val factory: ConnectionFabric
    private var connection: Connection = ConectionStub()
    private var channelSender: ChannelMessageSender = ChannelSenderStub()
    private val consumerFabric: ConsumerFabric
    private val messageListeners = ArrayList<NewMessageListener>()
    private val connectionListeners = ArrayList<ConnectionListener>()
    private val uiRunner: MainThreadNotifier
    private val log: Logger

    constructor(factory: ConnectionFabric,
                consumerFabric: ConsumerFabric,
                uiRunner: MainThreadNotifier,
                logger: Logger) {
        this.consumerFabric = consumerFabric
        this.factory = factory
        this.uiRunner = uiRunner
        this.log = logger
        consumerFabric.listener = MessageListener()
    }

    override fun connect() {
        val thread = Thread({
            doConnect()
        })
        thread.start()
    }

    private fun doConnect() {
        try {
            connection = factory.connection
            val channel = connection.createChannel(666)
            channelSender = ChannelSenderImpl(channel)
            channel.queueDeclare(QUEUE_NAME, false, false, false, null)
            channel.basicQos(1)

            val consumer = consumerFabric.provideConsumer(channel)

            val tag = channel.basicConsume(QUEUE_NAME, false, consumer)
            log.d(TAG, "consume = $tag")
            notifyConnected()
        } catch (e: Exception) {
            log.e(TAG, "doConnect: ", e)
        }
    }

    private fun notifyConnected() {
        uiRunner.runOnUiThread(Runnable {
            val iterator = connectionListeners.iterator()
            iterator.forEach {
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
            val iterator = messageListeners.iterator()
            iterator.forEach {
                it.onMessageReceived(message, chatInfo)
            }
        }
    }
}