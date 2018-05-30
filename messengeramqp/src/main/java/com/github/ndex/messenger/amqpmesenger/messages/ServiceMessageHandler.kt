package com.github.ndex.messenger.amqpmesenger.messages

import com.github.ndex.messenger.amqpmesenger.common.Serializer
import com.github.ndex.messenger.amqpmesenger.models.ChatListCommand
import com.github.ndex.messenger.amqpmesenger.models.ChatListCommand.Companion.ASSIGN_TO_CHAT
import com.github.ndex.messenger.amqpmesenger.models.ChatListCommand.Companion.CREATE_CHAT
import com.github.ndex.messenger.amqpmesenger.models.ChatListCommand.Companion.GET_CHAT_LIST
import com.github.ndex.messenger.amqpmesenger.models.ChatListCommand.Companion.INVITE_TO_CHAT
import com.github.ndex.messenger.interfaces.ChatInfo

typealias OnChatListUpdated = (List<ChatInfo>) -> Unit

class ServiceMessageHandler(private val serializer: Serializer) {
    var chatListUpdateListener: OnChatListUpdated = ChatListUpdateStub ()

    fun handleMessage(body: ByteArray) {
        val cmd = serializer.deserialize(body, ChatListCommand::class.java)
        when (cmd.cmd) {
            CREATE_CHAT -> println("Chat created")
            GET_CHAT_LIST -> chatListUpdateListener.invoke(cmd.chatList)
            INVITE_TO_CHAT -> onInvitedToChat(cmd)
            ASSIGN_TO_CHAT -> onAssignedToChat(cmd)
        }
    }

    private fun onInvitedToChat(cmd: ChatListCommand) {

    }

    private fun onAssignedToChat(cmd: ChatListCommand) {

    }

    private class ChatListUpdateStub: OnChatListUpdated {
        override fun invoke(p1: List<ChatInfo>) {
            /* stub */
        }
    }
}