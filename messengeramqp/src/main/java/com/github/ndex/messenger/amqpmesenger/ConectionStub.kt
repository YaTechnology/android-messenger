package com.github.ndex.messenger.amqpmesenger

import com.rabbitmq.client.*
import java.net.InetAddress

class ConectionStub : Connection {
    override fun setId(id: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getId(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeBlockedListener(listener: BlockedListener?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeShutdownListener(listener: ShutdownListener?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getHeartbeat(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun abort() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun abort(closeCode: Int, closeMessage: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun abort(timeout: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun abort(closeCode: Int, closeMessage: String?, timeout: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun notifyListeners() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isOpen(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPort(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getClientProvidedName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createChannel(): Channel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createChannel(channelNumber: Int): Channel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFrameMax(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearBlockedListeners() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun close() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun close(closeCode: Int, closeMessage: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun close(timeout: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun close(closeCode: Int, closeMessage: String?, timeout: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addBlockedListener(listener: BlockedListener?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChannelMax(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getExceptionHandler(): ExceptionHandler {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCloseReason(): ShutdownSignalException {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getClientProperties(): MutableMap<String, Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addShutdownListener(listener: ShutdownListener?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getServerProperties(): MutableMap<String, Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAddress(): InetAddress {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}