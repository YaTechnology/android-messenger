package com.github.ndex.messenger.demo_module.domain

import com.github.ndex.messenger.interfaces.*

class ClientStub : Client {
    override fun connect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disconnect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendMessage(message: Message, chatId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun registerNewMessageListener(listener: NewMessageListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unregisterNewMessageListener(listener: NewMessageListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun registerConnectionListener(listener: ConnectionListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unRegisterConnectionListener(listener: ConnectionListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun registerChatListChangedListener(listener: ChatListChangedListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unregisterChatListChangedListener(listener: ChatListChangedListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}