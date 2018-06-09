package com.github.ndex.messenger.amqpmesenger

import android.support.annotation.WorkerThread
import com.github.ndex.messenger.amqpmesenger.common.ChannelBinderImpl
import com.github.ndex.messenger.amqpmesenger.common.Logger
import com.github.ndex.messenger.amqpmesenger.common.MainThreadNotifier
import com.github.ndex.messenger.amqpmesenger.common.Serializer
import com.github.ndex.messenger.amqpmesenger.messages.ChannelSenderImpl
import com.github.ndex.messenger.amqpmesenger.messages.ChatMessageHandler
import com.github.ndex.messenger.amqpmesenger.messages.ServiceMessageHandler
import com.github.ndex.messenger.interfaces.*
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ShutdownListener
import com.rabbitmq.client.ShutdownSignalException
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


const val EXCHANGE_NAME = "messenger.topic"
const val SERVICE_QUEUE_NAME = "services"
private val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
private const val KEEP_ALIVE_TIME = 1L
private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS

class AmqpClient(private val factory: ConnectionFabric,
                 private val consumerFabric: ConsumerFabric,
                 private val serviceMessageHandler: ServiceMessageHandler,
                 private val chatMessageHandler: ChatMessageHandler,
                 private val uiRunner: MainThreadNotifier,
                 private val uuid: String,
                 private val serializer: Serializer,
                 logger: Logger) : Client {
    companion object {
        private val TAG = AmqpClient::class.java.simpleName
    }

    private val workQueue: BlockingQueue<Runnable> = LinkedBlockingQueue<Runnable>()
    private val executor = ThreadPoolExecutor(
            NUMBER_OF_CORES,       // Initial pool size
            NUMBER_OF_CORES,       // Max pool size
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            workQueue)

    private var connection: Connection = ConectionStub()
    private var channelSender: ChannelMessageSender = ChannelSenderStub()
    private val messageListeners = ArrayList<NewMessageListener>()
    private val connectionListeners = ArrayList<ConnectionListener>()
    private val chatListChangedListener = ArrayList<ChatListChangedListener>()
    private val log: Logger = logger
    private var channelBinder: ChannelBinder = ChannelBinderStub()
//    private var consumerFacade: ConsumerFacade = ConsumerFacadeStub()

    override fun connect() = executor.execute({ doConnect() })

    private fun doConnect() {
        try {
            connection = factory.connection
            val channel = connection.createChannel(666)
            val consumer = consumerFabric.provideConsumer(channel)

            channel.exchangeDeclare(EXCHANGE_NAME, "topic")
            channel.basicQos(1)

            channel.queueDeclare(uuid, false, false, false, null)
            channel.queueBind(uuid, EXCHANGE_NAME, uuid)
            channel.basicConsume(uuid, true, consumer)

            channel.addShutdownListener(ShutdownListenerImpl())

            channelSender = ChannelSenderImpl(channel)
            channelBinder = ChannelBinderImpl(uuid, channel, log)
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
        executor.execute({ doDisconnect() })
    }

    private fun doDisconnect() {
        try {
            connection.close()
        } catch (e: Exception) {
            log.e(TAG, "disconnect: ", e)
        }
    }

    override fun sendMessage(message: Message) {
        executor.execute({ channelSender.sendMessage(message) })
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

    override fun registerChatListChangedListener(listener: ChatListChangedListener) {
        chatListChangedListener.add(listener)
    }

    override fun unregisterChatListChangedListener(listener: ChatListChangedListener) {
        chatListChangedListener.remove(listener)
    }

    init {
        chatMessageHandler.listener = MessageListener()
        serviceMessageHandler.chatListUpdateListener = ::notifyChatListUpdated
    }

    @WorkerThread
    private fun notifyChatListUpdated(chatList: List<ChatInfo>) {
        chatListChangedListener.forEach {
            it.onChatListChanged(chatList)
        }

        bindToChats(chatList)
    }

    private fun bindToChats(chatList: List<ChatInfo>) {
        chatList.forEach {
            channelBinder.bind(it.id)
        }
    }

    private inner class MessageListener : NewMessageListener {
        override fun onMessageReceived(message: Message, chatInfo: ChatInfo) {
            messageListeners.forEach {
                it.onMessageReceived(message, chatInfo)
            }
        }
    }

    private inner class ShutdownListenerImpl : ShutdownListener {
        override fun shutdownCompleted(cause: ShutdownSignalException?) {
            log.e(TAG, "shutdownCompleted : $cause")
        }
    }

    private class ChannelBinderStub : ChannelBinder {
        override fun bind(chatQueue: String) {
            /* stub */
        }
    }
}